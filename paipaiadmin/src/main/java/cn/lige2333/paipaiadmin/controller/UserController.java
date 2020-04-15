package cn.lige2333.paipaiadmin.controller;

import cn.lige2333.paipaiadmin.Entity.Logs;
import cn.lige2333.paipaiadmin.service.LogService;
import cn.lige2333.paipaiadmin.service.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private LogService logService;
    @RequestMapping("/changeUser")
    @ResponseBody
    public String changeUser(Integer id, Integer status) {
        String s = usersService.setState(id, status);
        return s;
    }
    @RequestMapping("/changePro")
    @ResponseBody
    public String changePro(Integer id, Integer state) {
        String s = usersService.changeProState(id, state);
        return s;
    }
    @RequestMapping("/downLogExcel")
    public void downLogExcel(HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Logs> logs = mapper.readValue(logService.query(), new TypeReference<List<Logs>>() {
        });
        String[] header = {"ID", "操作", "模块", "用户", "操作时间"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("日志表");
        sheet.setDefaultColumnWidth(10);
        HSSFRow headrow = sheet.createRow(0);
        for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }
        for (int i = 1; i <=logs.size(); i++) {
            HSSFRow row = sheet.createRow(i);
            //创建一个单元格
            HSSFCell cell = row.createCell(0);
            HSSFRichTextString id = new HSSFRichTextString(logs.get(i-1).getId().toString());
            cell.setCellValue(id);
            HSSFCell cell2 = row.createCell(1);
            HSSFRichTextString description = new HSSFRichTextString(logs.get(i-1).getDescription());
            cell2.setCellValue(description);
            HSSFCell cell3 = row.createCell(2);
            HSSFRichTextString module = new HSSFRichTextString(logs.get(i-1).getModule());
            cell3.setCellValue(module);
            HSSFCell cell4 = row.createCell(3);
            HSSFRichTextString username = new HSSFRichTextString(logs.get(i-1).getUsername());
            cell4.setCellValue(username);
            HSSFCell cell5 = row.createCell(4);
            HSSFRichTextString createTime = new HSSFRichTextString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logs.get(i-1).getCreatetime()));
            cell5.setCellValue(createTime);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=logs.xls");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
