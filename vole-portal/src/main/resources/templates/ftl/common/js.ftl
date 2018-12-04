
<!-- 确认提示组件 -->
<div id="confirm-modal" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false" style="display: none;">
  <div class="modal-body">
    <p>您确定要删除该条记录吗?</p>
  </div>
  <div class="modal-footer">
    <button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
    <button type="button"  class="btn btn-primary del">删除</button>
  </div>
</div>

<!-- REQUIRED JS SCRIPTS -->
<!-- jQuery 2.1.4 -->
<script src="/static/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="/static/plugins/iCheck/icheck.min.js"></script>
<!-- nice-validator-1.0.8 -->
<script src="/static/plugins/nice-validator-1.0.8/jquery.validator.js?local=zh-CN"></script>
<!--jquery-confirm  -->
<script src="/static/plugins/jquery-confirm/jquery-confirm.min.js"></script>
<!-- Select2 -->
<script src="/static/plugins/select2/select2.full.min.js"></script>
<!-- date -->
<script src="/static/plugins/daterangepicker/moment.min.js"></script>
<script src="/static/plugins/daterangepicker/daterangepicker.js"></script>
<!-- jquery.cookie -->
<script src="/static/plugins/jquery.cookie.js"></script>

<!-- xlsx -->
<script src="/static/plugins/jquery.table2excel.js"></script>

<!-- AdminLTE App -->
<script src="/static/app/js/app.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="/static/app/js/demo.js"></script>
<!-- 自定义系统初始化话JS -->
<script src="/static/app/js/init.js"></script>

<script src="/static/plugins/layui/layui.js" charset="utf-8"></script>
<script src="/static/app/js/x-layui.js" charset="utf-8"></script>
<script type="text/javascript">
 	
$(function(){
	//select2
	$(".select2").select2();
	//iCheck for checkbox and radio inputs
	$('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
       checkboxClass: 'icheckbox_minimal-blue',
       radioClass: 'iradio_minimal-blue'
    });
});
</script>
<script type="text/javascript">
 layui.config({
	version:true,
	debug:true,
   	base: '/static/app/js/modules/'
 }).use('xform'); //加载入口

</script>


