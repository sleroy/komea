
package org.komea.product.web.rest.api;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DumpFilter implements Filter
{
    
    
    private class BufferedRequestWrapper extends HttpServletRequestWrapper
    {
        
        
        ByteArrayInputStream       bais;
        
        ByteArrayOutputStream      baos;
        
        BufferedServletInputStream bsis;
        
        byte[]                     buffer;
        
        
        
        public BufferedRequestWrapper(final HttpServletRequest req) throws IOException {
        
        
            super(req);
            final InputStream is = req.getInputStream();
            baos = new ByteArrayOutputStream();
            final byte buf[] = new byte[1024];
            int letti;
            while ((letti = is.read(buf)) > 0) {
                baos.write(buf, 0, letti);
            }
            buffer = baos.toByteArray();
        }
        
        
        public byte[] getBuffer() {
        
        
            return buffer;
        }
        
        
        @Override
        public ServletInputStream getInputStream() {
        
        
            try {
                bais = new ByteArrayInputStream(buffer);
                bsis = new BufferedServletInputStream(bais);
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
            
            return bsis;
        }
        
    }
    
    
    
    private class BufferedServletInputStream extends ServletInputStream
    {
        
        
        ByteArrayInputStream bais;
        
        
        
        public BufferedServletInputStream(final ByteArrayInputStream bais) {
        
        
            this.bais = bais;
        }
        
        
        @Override
        public int available() {
        
        
            return bais.available();
        }
        
        
        @Override
        public int read() {
        
        
            return bais.read();
        }
        
        
        @Override
        public int read(final byte[] buf, final int off, final int len) {
        
        
            return bais.read(buf, off, len);
        }
        
    }
    
    
    
    private static class ByteArrayPrintWriter
    {
        
        
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        private final PrintWriter           pw   = new PrintWriter(baos);
        
        private final ServletOutputStream   sos  = new ByteArrayServletStream(baos);
        
        
        
        public ServletOutputStream getStream() {
        
        
            return sos;
        }
        
        
        public PrintWriter getWriter() {
        
        
            return pw;
        }
        
        
        byte[] toByteArray() {
        
        
            return baos.toByteArray();
        }
    }
    
    
    
    private static class ByteArrayServletStream extends ServletOutputStream
    {
        
        
        ByteArrayOutputStream baos;
        
        
        
        ByteArrayServletStream(final ByteArrayOutputStream baos) {
        
        
            this.baos = baos;
        }
        
        
        @Override
        public void write(final int param) throws IOException {
        
        
            baos.write(param);
        }
    }
    
    
    
    private static final Logger LOGGER       = LoggerFactory.getLogger("logtransaction");
    private boolean             dumpRequest  = true;


    private boolean             dumpResponse = true;



    @Override
    public void destroy() {
    
    
    }
    
    
    @Override
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain) throws IOException, ServletException {


        if (!(servletRequest instanceof HttpServletRequest)) {
            return;
        }
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpRequest);

        if (dumpRequest) {
            LOGGER.info("REQUEST -> " + new String(bufferedRequest.getBuffer()));
            
            printRequestAttributes(httpRequest);
            
            printRequestParameters(httpRequest);
        }

        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        final ByteArrayPrintWriter pw = new ByteArrayPrintWriter();
        final HttpServletResponse wrappedResp = new HttpServletResponseWrapper(response)
        {


            @Override
            public ServletOutputStream getOutputStream() {


                return pw.getStream();
            }


            @Override
            public PrintWriter getWriter() {


                return pw.getWriter();
            }

        };

        filterChain.doFilter(bufferedRequest, wrappedResp);

        final byte[] bytes = pw.toByteArray();
        response.getOutputStream().write(bytes);
        if (dumpResponse) {
            LOGGER.info("RESPONSE -> " + new String(bytes));
        }
    }


    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    
    
        dumpRequest = Boolean.valueOf(filterConfig.getInitParameter("dumpRequest"));
        dumpResponse = Boolean.valueOf(filterConfig.getInitParameter("dumpResponse"));
    }
    
    
    /**
     * This method prints all the request attributes that are
     * available on the HttpServletRequest.
     * The result will be printed back to the browser.
     */
    private void printRequestAttributes(final HttpServletRequest req) {
    
    
        LOGGER.info("IN---- {}", req.getRequestURI());
        
        final Enumeration<String> requestAttributes = req.getAttributeNames();
        while (requestAttributes.hasMoreElements()) {
            final String attributeName = requestAttributes.nextElement();
            LOGGER.info("REQUEST + attr {} : value {} ", attributeName,
                    req.getAttribute(attributeName).toString());
        }
        LOGGER.info("");
    }
    
    
    /**
     * This method prints all the request parameters that are
     * available on the HttpServletRequest.
     * The result will be printed back to the browser
     */
    private void printRequestParameters(final HttpServletRequest req) {


        final Enumeration<String> requestParameters = req.getParameterNames();
        while (requestParameters.hasMoreElements()) {
            final String paramName = requestParameters.nextElement();
            LOGGER.info("REQUEST + param {} : value {} ", paramName,
                    req.getAttribute(req.getParameter(paramName)).toString());
            
        }
    }
}
