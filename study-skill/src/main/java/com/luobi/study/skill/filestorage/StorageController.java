package com.luobi.study.skill.filestorage;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/storage")
@Validated
@Slf4j
public class StorageController {

    private static final String FILE_ROOT_PATH = "./file/";  // 相对路径

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public JSONObject uploadFile(@NotNull(message = "文件不能为空") MultipartFile file) {
        JSONObject resp = new JSONObject();

        File localFile = multipartToFile(file);
        if (Objects.isNull(localFile)) {
            resp.put("success", false);
            resp.put("code", 400);
            resp.put("msg", "文件上传失败");
            return resp;
        }

        resp.put("success", true);
        resp.put("code", 200);
        resp.put("msg", "上传成功");
        resp.put("date", localFile.getName());
        return resp;
    }

    private File multipartToFile(MultipartFile multipartFile) {
        try {
            File tmpFile = new File(FILE_ROOT_PATH
                    + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now())
                    + "_"
                    + multipartFile.getOriginalFilename()
            );
            if (!tmpFile.exists()) {
                tmpFile.getParentFile().mkdirs();
                tmpFile.createNewFile();
            }
            try (FileOutputStream fos = new FileOutputStream(tmpFile)) {
                fos.write(multipartFile.getBytes());
                fos.close();
                return tmpFile;
            }
        } catch (IOException e) {
            log.error("[Error] Failed to upload file.", e);
        }
        return null;
    }

    /**
     * 查询文件列表
     */
    @GetMapping("list")
    public JSONObject listFile() {
        JSONObject resp = new JSONObject();

        try {
            File directory = new File(FILE_ROOT_PATH);
            resp.put("success", true);
            resp.put("code", 200);
            resp.put("msg", "查询成功");
            resp.put("date", Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                    .sorted(Comparator.comparing(file -> Long.valueOf(file.getName().split("_")[0])))
                    .sorted(Comparator.reverseOrder())
                    .map(File::getName)
                    .collect(Collectors.toList())
            );
            return resp;
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("code", 400);
            resp.put("msg", "查询失败");
            resp.put("data", e.getMessage());
            return resp;
        }
    }

    /**
     * 下载文件
     */
    @GetMapping("/download")
    public void downloadFile(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''"
                    + URLEncoder.encode(fileName, "UTF-8"));
            IOUtils.copy(Files.newInputStream(Paths.get(FILE_ROOT_PATH + fileName)), response.getOutputStream());
        } catch (Exception e) {
            JSONObject resp = new JSONObject();
            resp.put("success", false);
            resp.put("code", 400);
            resp.put("msg", "文件下载失败");
            resp.put("date", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(resp.toJSONString());
        }
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/delete")
    public JSONObject deleteFile(@RequestBody String fileName) {
        JSONObject resp = new JSONObject();

        File file = new File(FILE_ROOT_PATH + fileName);
        try {
            FileUtils.forceDelete(file);
        } catch (FileNotFoundException e) {
            log.error("Failed to delete file : " + e.getMessage());
            resp.put("success", false);
            resp.put("code", 300);
            resp.put("msg", "文件不存在");
            resp.put("data", e.getMessage());
            return resp;
        } catch (Exception e) {
            log.error("Failed to delete file : " + e.getMessage());
            resp.put("success", false);
            resp.put("code", 300);
            resp.put("msg", "文件删除失败");
            resp.put("data", e.getMessage());
            return resp;
        }

        resp.put("success", true);
        resp.put("code", 200);
        resp.put("msg", "删除成功");
        resp.put("date", fileName);
        return resp;
    }

}
