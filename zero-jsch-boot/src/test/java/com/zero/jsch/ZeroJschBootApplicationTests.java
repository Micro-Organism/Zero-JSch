package com.zero.jsch;

import com.alibaba.fastjson2.JSONObject;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.zero.jsch.common.model.Remote;
import com.zero.jsch.common.util.JSchUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@SpringBootTest
class ZeroJschBootApplicationTests {

    Session session;

    @BeforeEach
    public void before() throws JSchException {
        Remote remote = new Remote();
        remote.setHost("xxx.xxx.xxx.xxx");
        remote.setUser("root");
        remote.setPassword("xxxx");
        session = JSchUtil.getSession(remote);
    }

    @AfterEach
    public void after() {
        JSchUtil.disconnect(session);
    }

    @Test
    public void remoteExecute() throws JSchException {
        List<String> list = JSchUtil.remoteExecute(session, "ls");
        System.out.println(JSONObject.toJSONString(list));
    }

    @Test
    public void uploadFile() throws JSchException, FileNotFoundException {
        String filestr = "D:\\tmp\\test\\file_utils\\file1.txt";
        File file = new File(filestr);
        InputStream in = new FileInputStream(file);

        String directory = "/root/test";
        String fileName = "test.txt";
        boolean flag = JSchUtil.uploadFile(session, in, directory, fileName);
        System.out.println(flag);
    }

    @Test
    public void deleteFile() throws JSchException, FileNotFoundException {
        String directory = "/root/test";
        String fileName = "test.txt";
        boolean flag = JSchUtil.deleteFile(session, directory, fileName);
        System.out.println(flag);
    }

    @Test
    public void scpFrom() throws JSchException, FileNotFoundException {

        String source = "/root/test/file1.txt";
        String destination = "D:\\tmp\\scfFrom.txt";
        long filesize = JSchUtil.scpFrom(session, source, destination);
        System.out.println(filesize);
    }

    @Test
    public void scpTo() throws JSchException, FileNotFoundException {

        String filestr = "D:\\tmp\\test\\file_utils\\file1.txt";
        String destination = "/root/test/file1.txt";
        long filesize = JSchUtil.scpTo(session, filestr, destination);
        System.out.println(filesize);
    }

}
