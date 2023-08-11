package tw.gov.pcc.web.rest.io;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import static java.util.Objects.requireNonNull;

/**
 * 可供下載的資源，跟一般的 {@link Resource} 不同的地方只是多了可以覆寫檔名的機制。
 */
public class DownloadableResource implements Resource {

    private final Resource resource;

    private final String filename;

    /**
     * 建立 {@code DownloadableResource} 物件。
     *
     * @param resource
     *            資源。
     */
    public DownloadableResource(Resource resource) {
        this(requireNonNull(resource, "Parameter \"resource\" should not be null."), resource.getFilename());
    }

    /**
     * 指定檔名，建立 {@code DownloadableResource} 物件。
     *
     * @param resource
     *            資源。
     * @param filename
     *            檔案名稱。
     */
    public DownloadableResource(Resource resource, String filename) {
        this.resource = requireNonNull(resource, "Parameter \"resource\" should not be null.");
        this.filename = requireNonNull(filename, "Parameter \"filename\" should not be null.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists() {
        return resource.exists();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReadable() {
        return resource.isReadable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOpen() {
        return resource.isOpen();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getURL() throws IOException {
        return resource.getURL();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URI getURI() throws IOException {
        return resource.getURI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getFile() throws IOException {
        return resource.getFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long contentLength() throws IOException {
        return resource.contentLength();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long lastModified() throws IOException {
        return resource.lastModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return resource.createRelative(relativePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return resource.getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilename() {
        return filename;
    }
}
