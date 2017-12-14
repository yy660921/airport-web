<%@ page language="java" pageEncoding="UTF-8" %>
<c:set var="stx" value="${pageContext.request.servletPath}"/>
<style>
    .footer-links li{
        color: white;
    }
</style>
<div id="footer" style="height: 70px;background-color: #3f5c7c;margin-bottom: 0;">
<div class="foot-center" style="margin-top: 15px;">
    <ul class="footer-links">
    </ul>
    <div style="color: white;">Copyright &copy; 2013-2017北航计算机学院新技术研究所（ACT）<a
            target="_blank" href="http://act.buaa.edu.cn">act.buaa.edu.cn</a></div>
</div>
</div>
<div id="myModal" class="modal fade">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span aria-hidden="true">&times;</span></button>
                    <h3 id="myModalLabel" style="text-align: center;">问题反馈</h3>
                </div>

                <div class="modal-body">
                    <form id="feedForm" action="/feed" method="post" autocomplete="off">
                        <table cellspacing="0">
                            <tr>
                                <td width="15%" style="padding-top: 5px;">问题类型*</td>
                                <td><label class="radio inline"> <input type="radio" name="problem" value="界面视觉"
                                                                        checked> 界面
                                </label> <label class="radio inline"> <input type="radio" name="problem"
                                                                             value="网站错误"> 网站错误
                                </label> <label class="radio inline"> <input type="radio" name="problem"
                                                                             value="功能建议"> 功能建议
                                </label></td>
                            </tr>
                            <tr>
                                <th width="15%" style="padding-top: 5px;">反馈内容*</th>
                                <td><textarea name="message" class="input-sincerely input-block-level"
                                              style="height: 200px;" maxlength="1000" cols="90"></textarea></td>
                            </tr>
                            <tr>
                                <th width="15%" style="padding-top: 5px;">电子邮箱</th>
                                <td><input type="text" name="email" value="" maxlength="30"></td>
                            </tr>
                        </table>
                        <input class="btn btn-sincerely btn-success btn-tall pull-right" type="submit"
                               value="提交 ">
                    </form>
                </div>
            </div>
        </div>
</div>

<script>
</script>

