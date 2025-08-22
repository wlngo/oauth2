package top.wei.oauth2.security.login.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import top.wei.oauth2.utils.JackSonUtils;
import top.wei.oauth2.utils.Rest;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * The type Responser.
 */
@Slf4j
public abstract class ResponseWriter {

    /**
     * Write.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException the io exception
     */
    public void write(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String resBody = JackSonUtils.getDefaultObjectMapper().writeValueAsString(this.body(request));
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }

    /**
     * Msg string.
     *
     * @param request the request
     * @return the string
     */
    protected abstract Rest<?> body(HttpServletRequest request);

}
