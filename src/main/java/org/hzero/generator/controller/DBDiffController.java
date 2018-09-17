package org.hzero.generator.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.generator.service.IDBDiffService;
import org.hzero.generator.util.GeneratorUtils;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys/db")
public class DBDiffController {

    @Autowired
    private IDBDiffService dBDiffService;

    @RequestMapping("/diff")
    public void codeByDDD(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sourceEnv = request.getParameter("sourceEnv");
        String sourceDB = request.getParameter("sourceDB");
        String targetEnv = request.getParameter("targetEnv");
        String targetDB = request.getParameter("targetDB");
        if (StringUtils.isBlank(sourceEnv) || StringUtils.isBlank(sourceDB) || StringUtils.isBlank(targetEnv)
                        || StringUtils.isBlank(targetDB)) {
            return;
        }
        Document document = dBDiffService.compareDiff(sourceEnv, sourceDB, targetEnv, targetDB);
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());//设置文本格式
        String result = outputter.outputString(document);
        byte[] data = result.getBytes(GeneratorUtils.DEFAULT_CHARACTER_SET);
        response.setHeader("Content-Disposition", "attachment; filename=" + targetEnv+"-"+targetDB+ ".xml");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset="+GeneratorUtils.DEFAULT_CHARACTER_SET);
        IOUtils.write(data, response.getOutputStream());
    }

}
