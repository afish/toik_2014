package pl.agh.iet.i.toik.cloudsync.onedrive.util;

import org.apache.commons.io.IOUtils;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JSONResolverTest {

    private JSONResolver jsonResolver;

    private String parentPath = "comic/sans";
    private CloudFile mockParent;

    @Before
    public void setUp() throws Exception {
        jsonResolver = new JSONResolver();
        mockParent = mock(CloudFile.class);

        when(mockParent.getFullPath()).thenReturn(parentPath);
    }

    @Test
    public void testListingFilesFromProperFiles() throws Exception {
        String responseContent = loadJSONResponseFromFile("file_service/properFilesList.json");

        List<CloudFile> result = jsonResolver.resolveFileListDetails(responseContent, mockParent);

        assertEquals(6, result.size());

        CloudFile documentsFolder = findCloudFileById(result, "folder.fa6a470fcef1fb9a.FA6A470FCEF1FB9A!106");
        assertNotNull(documentsFolder);
        assertEquals("Documents", documentsFolder.getName());
        assertEquals(825522, documentsFolder.getSize().longValue());
        assertEquals(resolvePath("Documents"), documentsFolder.getFullPath());
        assertTrue(documentsFolder.isDirectory());
        assertEquals(getDateFromString("2014-05-11T21:27:58+0000"), documentsFolder.getCreationDate());

        CloudFile textFile = findCloudFileById(result, "file.fa6a470fcef1fb9a.FA6A470FCEF1FB9A!118");
        assertNotNull(textFile);
        assertEquals("Bez nazwy.txt", textFile.getName());
        assertEquals(4, textFile.getSize().longValue());
        assertEquals(resolvePath("Bez nazwy.txt"), textFile.getFullPath());
        assertFalse(textFile.isDirectory());
        assertEquals(getDateFromString("2014-05-17T19:34:04+0000"), textFile.getCreationDate());
    }

    @Test
    public void testListingFilesFromWrongFile() throws Exception {
        String responseContent = loadJSONResponseFromFile("file_service/wrongFilesList.json");

        List<CloudFile> result = jsonResolver.resolveFileListDetails(responseContent, mockParent);

        assertEquals(1, result.size());
        assertNotNull(findCloudFileById(result, "file.fa6a470fcef1fb9a.FA6A470FCEF1FB9A!132"));
        assertNull(findCloudFileById(result, "file.fa6a470fcef1fb9a.FA6A470FCEF1FB9A!134"));
    }

    @Test
    public void testEmptyFilesList() throws Exception {
        String responseContent = loadJSONResponseFromFile("file_service/emptyFilesList.json");

        assertTrue(jsonResolver.resolveFileListDetails(responseContent, mockParent).isEmpty());
    }

    @Test
    public void testProperFileDetails() throws Exception {
        String responseContent = loadJSONResponseFromFile("file_service/properFileDetails.json");

        CloudFile result = jsonResolver.resolveUploadedFileDetails(responseContent, mockParent);

        assertNotNull(result);
        assertEquals("test.txt", result.getName());
        assertEquals(12, result.getSize().longValue());
        assertEquals(resolvePath("test.txt"), result.getFullPath());
        assertFalse(result.isDirectory());
        assertEquals(getDateFromString("2014-06-03T20:17:00+0000"), result.getCreationDate());
    }

    @Test
    public void testWrongFileDetails() throws Exception {
        String responseContent = loadJSONResponseFromFile("file_service/wrongFileDetails.json");

        assertNull(jsonResolver.resolveUploadedFileDetails(responseContent, mockParent));
    }

    @Test
    public void testProperlyUploadedFile() throws Exception {
        String responseContent = loadJSONResponseFromFile("file_service/properlyUploadedFile.json");

        String response = jsonResolver.resolveUploadedFileId(responseContent);

        assertNotNull(response);
        assertEquals("file.fa6a470fcef1fb9a.FA6A470FCEF1FB9A!134", response);
    }

    @Test
    public void testWronglyUploadedFile() throws Exception {
        String responseContent = loadJSONResponseFromFile("file_service/wronglyUploadedFile.json");

        assertNull(jsonResolver.resolveUploadedFileId(responseContent));
    }

    private String loadJSONResponseFromFile(String filename) throws IOException {
        URL filesListUrl = JSONResolverTest.class.getClassLoader().getResource(filename);
        assert filesListUrl != null;
        return IOUtils.toString(filesListUrl.openStream());
    }

    private String resolvePath(String filename) {
        return parentPath + JSONResolver.separator + filename;
    }

    private CloudFile findCloudFileById(List<CloudFile> fileList, String id) {
        for (CloudFile file : fileList) {
            if (file.getId().equals(id)) {
                return file;
            }
        }
        return null;
    }

    private Date getDateFromString(String date) {
        return DateTimeFormat.forPattern(JSONResolver.dateTimeFormat).parseLocalDateTime(date).toDate();
    }
}