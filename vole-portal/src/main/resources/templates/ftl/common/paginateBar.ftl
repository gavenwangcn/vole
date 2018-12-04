<#macro paginate pageData actionUrl urlParas="">
 	每页 <select class="form-control" name="size" style="width: auto;display: initial;margin: 0 5px;" onchange="changePage(this)">
      <option value="15" ${(pageData.size==15)?string('selected="selected"','')}  >15条</option>
      <option value="30" ${(pageData.size==30)?string('selected="selected"','')}  >30条</option>
      <option value="50" ${(pageData.size==50)?string('selected="selected"','')}  >50条</option>
      <option value="100" ${(pageData.size==100)?string('selected="selected"','')}  >100条</option>
      <option value="200" ${(pageData.size==200)?string('selected="selected"','')}  >200条</option>
    </select>条
 	显示 ${((pageData.current -1) * pageData.size + 1)!}  -  ${(pageData.current * pageData.size)!} 条  共 ${(pageData.getTotal())!} 条记录
 <script>
 	function changePage(obj){
 		window.location.href="${actionUrl}#{1}${urlParas}&pageSize="+$(obj).val();
 	}
 </script>
</#macro>