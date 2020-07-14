package by.gradomski.apartments.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@SuppressWarnings("serial")
public class DateTimeTag extends TagSupport {
    private static final DateTimeFormatter format = DateTimeFormatter.
            ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);
    private LocalDateTime dateTime;
    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }

    @Override
    public int doStartTag() throws JspException {
        String dateTimeString = dateTime.format(format);
        try {
            pageContext.getOut().write("<h4>" + dateTimeString + "</h4>");
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
