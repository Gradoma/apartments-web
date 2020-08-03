package by.gradomski.apartments.command.impl;

import by.gradomski.apartments.command.Command;
import by.gradomski.apartments.controller.Router;
import by.gradomski.apartments.entity.*;
import by.gradomski.apartments.exception.ServiceException;
import by.gradomski.apartments.mail.MailConstructor;
import by.gradomski.apartments.mail.MailSender;
import by.gradomski.apartments.service.impl.AdServiceImpl;
import by.gradomski.apartments.service.impl.ApartmentServiceImpl;
import by.gradomski.apartments.service.impl.DemandServiceImpl;
import by.gradomski.apartments.service.impl.UserServiceImpl;
import by.gradomski.apartments.util.PageCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static by.gradomski.apartments.command.PagePath.ADMIN_APARTMENTS;
import static by.gradomski.apartments.command.PagePath.ERROR_PAGE;

public class AdminBanAdvertisementCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private static final String EMAIL_SUBJECT = "You have a Ban!";
    private static final String ID = "id";
    private static final String ADVERTISEMENT = "advertisement";
    private static final String ADVERTISEMENT_LIST = "advertisementList";
    private static final String PAGES_AMOUNT = "pagesAmount";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page;
        String advertisementIdString = request.getParameter(ID);
        long advertisementId = Long.parseLong(advertisementIdString);
        try {
            Advertisement advertisement = AdServiceImpl.getInstance().getAdById(advertisementId);
            long apartmentId = advertisement.getApartmentId();
            List<Demand> demandList = DemandServiceImpl.getInstance()
                    .getActiveDemandsByApartmentId(apartmentId);
            if(advertisement.isVisible() == true){
                AdServiceImpl.getInstance().changeVisibility(advertisementId);
            }
            for(Demand demand : demandList){
                switch (demand.getStatus()){
                    case CREATED:
                        DemandServiceImpl.getInstance().refuseDemand(demand.getId());

                        break;
                    case APPROVED:
                        DemandServiceImpl.getInstance().cancelDemand(demand.getId());
                        break;
                }
            }
            ApartmentServiceImpl.getInstance().updateApartmentStatus(apartmentId, ApartmentStatus.REGISTERED);
            List<Advertisement> advertisementList = (List<Advertisement>) request.getServletContext().getAttribute(ADVERTISEMENT_LIST);
            advertisementList.remove(advertisement);
            request.getServletContext().setAttribute(ADVERTISEMENT_LIST, advertisementList);
            int pages = PageCounter.countPages(advertisementList);
            request.getServletContext().setAttribute(PAGES_AMOUNT, pages);
//            int size = advertisementList.size();
//            int pages = (int) Math.ceil(size/ON_PAGE);
//            int[] arrayPages = new int[pages];
//            request.getServletContext().setAttribute(PAGES_AMOUNT, arrayPages);
            long authorId = advertisement.getAuthorId();
            log.debug("author id = " + authorId);
            User author = UserServiceImpl.getInstance().getUserById(authorId);
            String advertisementTitle = advertisement.getTitle();
            String mailText = MailConstructor.banMail(author.getLoginName(), ADVERTISEMENT, advertisementTitle);
            MailSender sender = new MailSender(author.getMail(), EMAIL_SUBJECT, mailText);
            sender.send();
            page = ADMIN_APARTMENTS;
        } catch (ServiceException e){
            log.error(e);
            page = ERROR_PAGE;
        }
        router.setPage(page);
        return router;
    }
}
