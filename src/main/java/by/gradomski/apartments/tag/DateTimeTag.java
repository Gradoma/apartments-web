package by.gradomski.apartments.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@SuppressWarnings("serial")
public class DateTimeTag extends TagSupport {
    private static final DateTimeFormatter format = DateTimeFormatter.
            ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);
    private LocalDateTime dateTimeValue;

    public void setDateTimeValue(LocalDateTime dateTimeValue){
        this.dateTimeValue = dateTimeValue;
    }
    private static final Logger log = LogManager.getLogger();

    @Override
    public int doStartTag() throws JspException {
        log.info("localDatetime=" + dateTimeValue);
        String dateTimeString = dateTimeValue.format(format);
        log.info("formated Date time=" + dateTimeString);
        try {
            pageContext.getOut().write(dateTimeString);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
