package com.web2unix.velocitycli.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

@Controller
public class ManagerController {

    @GetMapping("/manager")
    public String manager(Model model) {
        return "manager";
    }

    @PostMapping("/preview")
    public String process(
            @RequestParam("file") MultipartFile file,
            @RequestParam("keys") String[] k,
            @RequestParam("values") String[] v,
            HttpServletResponse response) throws Exception {

        InputStream inputStream = file.getInputStream();
        String html = new String(StreamUtils.copyToByteArray(inputStream), UTF_8);

        Velocity.init();
        VelocityContext velocityContext = new VelocityContext();
        for (int i = 0; i < k.length; i++) {
            if (i < v.length) {
                velocityContext.put(k[i], v[i]);
            }
        }

        StringWriter writer = new StringWriter();
        Velocity.evaluate(velocityContext, writer, "Plantilla HTML", html);
        String parsedHtml = writer.toString();

        response.setContentType("text/html");
        response.getWriter().write(parsedHtml);
        return parsedHtml;
    }
}
