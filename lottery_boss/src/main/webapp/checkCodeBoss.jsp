<%@ page contentType="image/jpeg" import="com.npc.lottery.util.NumberUtils" %> 
<%@ page contentType="image/jpeg" import="com.npc.lottery.util.CheckCodeVo" %> 
<%@ page contentType="image/jpeg" import="java.awt.*" %> 
<%@ page contentType="image/jpeg" import="java.awt.image.*" %> 
<%@ page contentType="image/jpeg" import="javax.imageio.*" %> 
<% 
//设置页面不缓存 
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 

CheckCodeVo checkCodeVo = new NumberUtils().generate(false);
String checkCode = checkCodeVo.getCheckCode();
BufferedImage image = checkCodeVo.getImage();

session.setAttribute(com.npc.lottery.common.Constant.LOGIN_CODE,checkCode); 


// 清空之前的流
out.clear(); 
// 输出图象到页面 
ImageIO.write(image, "JPEG", response.getOutputStream()); 
out = pageContext.pushBody();
%> 
