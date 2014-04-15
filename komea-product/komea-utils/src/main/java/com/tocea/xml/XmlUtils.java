/**
 * JavaDistiller Refactoring API.
 * <p>
 * 
 * @author sleroy
 * @version $LastChangedRevision: 1833 $ $LastChangedDate: 2010-07-07 13:50:55 +0200 (mer., 07 juil. 2010) $
 * @since 27 mars 2009
 * @copyright Copyright (C) 2008-2009 IRISA/INRIA Property.
 */

package com.tocea.xml;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;



/**
 * This utility class provides some methods to manipulate XML representations.
 * 
 * @copyright Copyright (c) 2008 INRIA/Irisa
 */
public final class XmlUtils
{
    
    
    private static final int    DEFAULT_BUFFER_SIZE = 80000;
    
    /**
     * This field describes
     */
    private static final String EXCEPTION_HAPPENED  = "Exception happened "; //$NON-NLS-1$
                                                                             
                                                                             
    
    /**
     * This method performs the serialization with xstream, gzipped.
     * 
     * @param _object
     *            the object
     * @param _outputFile
     *            the output file.
     * @throws IOException
     */
    public static <T> void serializationXStream(final T _object, final File _outputFile)
            throws IOException {
    
    
        XmlUtils.serializationXStream(_object, _outputFile, true);
    }
    
    
    /**
     * This method performs the serialization with xstream, gzipped.
     * 
     * @param _object
     *            the object
     * @param _outputFile
     *            the output file.
     * @param _zcompressed
     *            test if the parameter is compressed.
     * @throws IOException
     */
    public static <T> void serializationXStream(
            final T _object,
            final File _outputFile,
            final boolean _zcompressed) throws IOException {
    
    
        /**
         * Generation du XStream.
         */
        final XStream xstream = new XStream(new XppDriver());
        OutputStream bufferedOutputStream = null;
        try {
            final File file = _outputFile;
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            if (_zcompressed) {
                bufferedOutputStream = new GZIPOutputStream(bufferedOutputStream);
                xstream.toXML(_object, new OutputStreamWriter(bufferedOutputStream));
            } else {
                xstream.toXML(_object, new OutputStreamWriter(bufferedOutputStream));
            }
            
        } finally {
            IOUtils.closeQuietly(bufferedOutputStream);
        }
    }
    
    
    /**
     * This method loads the audit representation.
     * 
     * @param _xstreamFile
     *            the xstream file.
     * @return the results directory.
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static <T> T unserializationXStream(final File _xstreamFile) throws IOException {
    
    
        return (T) XmlUtils.unserializationXStream(_xstreamFile, true);
    }
    
    
    /**
     * This method loads the audit representation.
     * 
     * @param _xstreamFile
     *            the xstream file.
     * @param _compressed
     *            use of compression
     * @return the results directory.
     * @throws IOException
     */
    public static <T> T unserializationXStream(final File _xstreamFile, final boolean _compressed)
            throws IOException {
    
    
        T res = null;
        final XStream xStream = new XStream(new XppDriver());
        InputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(_xstreamFile));
            
            if (_compressed) {
                bufferedInputStream = new GZIPInputStream(bufferedInputStream);
            }
            res = (T) xStream.fromXML(new InputStreamReader(bufferedInputStream));
            
        } finally {
            IOUtils.closeQuietly(bufferedInputStream);
        }
        return res;
    }
    
    
    /**
     * Private Deprecated Constructor. (Utility class)
     */
    private XmlUtils() {
    
    
        super();
        
        // Private Constructor
    }
}
