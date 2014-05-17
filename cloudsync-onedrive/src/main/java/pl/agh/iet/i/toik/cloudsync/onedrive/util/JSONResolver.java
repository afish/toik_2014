package pl.agh.iet.i.toik.cloudsync.onedrive.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class JSONResolver {
    private static final String separator = "/";
    private static final String dateTimeFormat = "YY-MM-dd'T'HH:mm:ssZ";

    private static final String filesArrayJSONAttribute = "data";

    private static final String fileIdJSONAttribute = "id";
    private static final String fileNameJSONAttribute = "name";
    private static final String fileCreatedTimeJSONAttribute = "created_time";

    private static final String fileTypeJSONAttribute = "type";
    private static final String folderJSONValue = "folder";

    public List<CloudFile> getCloudFilesListFromJSON(String jsonClientResponse, CloudFile parent) {
        List<CloudFile> filesList = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(jsonClientResponse);
        JSONArray jsonFiles = jsonResponse.getJSONArray(filesArrayJSONAttribute);

        for (int i = 0; i < jsonFiles.length(); ++i) {
            JSONObject jsonFileDetails = jsonFiles.getJSONObject(i);
            CloudFile file = new CloudFile(
                    jsonFileDetails.getString(fileNameJSONAttribute),
                    getCreatedDate(jsonFileDetails),
                    isDirectory(jsonFileDetails),
                    resolvePath(parent, jsonFileDetails),
                    jsonFileDetails.getString(fileIdJSONAttribute)
            );
            filesList.add(file);
        }
        return filesList;
    }

    private boolean isDirectory(JSONObject jsonFileDetails) {
        return jsonFileDetails.getString(fileTypeJSONAttribute).equals(folderJSONValue);
    }

    //Temporary solution
    private String resolvePath(CloudFile parent, JSONObject jsonFileDetails) {
        String parentPath;

        if (StringUtils.isEmpty(parent.getFullPath())) {
            parentPath = "";
        } else {
            parentPath = parent.getFullPath();
        }

        return parentPath + separator + jsonFileDetails.getString(fileNameJSONAttribute);
    }

    private Date getCreatedDate(JSONObject jsonFileDetails) {
        return DateTimeFormat.forPattern(dateTimeFormat).parseLocalDateTime(jsonFileDetails.getString(fileCreatedTimeJSONAttribute)).toDate();
    }


}
