package com.fastjavaframework.maker.support.html;

/**
 * 模块html
 *
 * @author wangshuli
 */
public class ModuleHelperHtml {

    public String html() {
        StringBuffer sb = new StringBuffer();
        String newLine = System.getProperty("line.separator");

        sb.append(newLine).append("<div class=\"infoPage\">")
                .append(newLine).append("	")
                .append(newLine).append("	<div class=\"content\" style=\"height:230px\">")
                .append(newLine).append("		<div class=\"title\"></div>")
                .append(newLine).append("		<div class=\"margin\">")
                .append(newLine).append("			<font>* 配置在spring boot配置文件中</font>")
                .append(newLine).append("		</div>")
                .append(newLine).append("		<textarea id=\"errText\" readonly=\"readonly\" class=\"margin\" style=\"margin-top:0px;height:140px\">")
                .append(newLine).append("fastjava:")
                .append(newLine).append("  options-auhority: true #扫描有@Authority的类和方法，SystemSetting中获取 结果")
                .append(newLine).append("  exception:")
                .append(newLine).append("    message: \"系统异常，请稍后再试！\" #throw new ThrowException(\"XXX\");不设置按异常抛信息，设置全局按设置抛信息。")
                .append(newLine).append("</textarea>")
                .append(newLine).append("	</div>")
                .append(newLine).append("	")
                .append(newLine).append("</div>")
                .append(newLine).append("")
                .append(newLine).append("<script>")
                .append(newLine).append("//读取项目路径")
                .append(newLine).append("function readPath() {")
                .append(newLine).append("	doFastJava('moduleHelper'); //读取项目路径")
                .append(newLine).append("}")
                .append(newLine).append("</script>")
                .append(newLine).append("<style>")
                .append(newLine).append(".infoPage {")
                .append(newLine).append("	margin-left: auto;")
                .append(newLine).append("	margin-right:auto;")
                .append(newLine).append("	width:970px;")
                .append(newLine).append("}")
                .append(newLine).append(".content {")
                .append(newLine).append("	background-color: white;")
                .append(newLine).append("	height:510px;")
                .append(newLine).append("	color:#4E4E4E;")
                .append(newLine).append("	margin-bottom: 20px;")
                .append(newLine).append("}")
                .append(newLine).append(".path {")
                .append(newLine).append("	height:60px;")
                .append(newLine).append("	padding-top:10px;")
                .append(newLine).append("	padding-left:35px;")
                .append(newLine).append("}")
                .append(newLine).append(".xmlPath {")
                .append(newLine).append("	width: 750px;")
                .append(newLine).append("}")
                .append(newLine).append(".title {")
                .append(newLine).append("	height:40px;")
                .append(newLine).append("	line-height:40px;")
                .append(newLine).append("	border-bottom:1px solid rgba(0,0,0,.15);")
                .append(newLine).append("	padding-left:10px;")
                .append(newLine).append("	font-size:16px;")
                .append(newLine).append("}")
                .append(newLine).append(".margin {")
                .append(newLine).append("	margin: 15px 35px 0px;")
                .append(newLine).append("}")
                .append(newLine).append("textarea {")
                .append(newLine).append("	resize: none;")
                .append(newLine).append("	width: 900px;")
                .append(newLine).append("	font-size:16px;")
                .append(newLine).append("	font-family: Microsoft Yahei,Helvetica Neue,Hiragino Sans GB,WenQuanYi Micro Hei,sans-serif")
                .append(newLine).append("}")
                .append(newLine).append("</style>");

        return sb.toString();
    }
}
