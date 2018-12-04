<#include "/ftl/common/layout.ftl">
<@header>
</@header>
<@body>
<div class="content-wrapper">
  <!-- Content Header (Page header) -->
  <section class="content-header">
    <h1>
      	主面板
      <small>显示系统信息</small>
    </h1>
  </section>

  <!-- Main content -->
  <section class="content">
	
	<div class="row">
	  <div class="col-lg-3 col-xs-6">
         <!-- small box -->
         <div class="small-box  bg-yellow">
           <div class="inner">
             <h3>${(sm?string(',##0.00'))!'0.00'}</h3>
             <p>今日收款</p>
           </div>
           <div class="icon">
             <i class="fa fa-jpy"></i>
           </div>
           <a href="#" class="small-box-footer">详细信息 <i class="fa fa-arrow-circle-right"></i></a>
         </div>
       </div><!-- ./col -->
       
       <div class="col-lg-3 col-xs-6">
         <!-- small box -->
         <div class="small-box bg-aqua">
           <div class="inner">
             <h3>${(ct)!'0'}</h3>
             <p>今日订单数</p>
           </div>
           <div class="icon">
             <i class="fa fa-cart-plus"></i>
           </div>
           <a href="#" class="small-box-footer">详细信息 <i class="fa fa-arrow-circle-right"></i></a>
         </div>
       </div><!-- ./col -->
       
         <div class="col-lg-3 col-xs-6">
         <!-- small box -->
         <div class="small-box  bg-red">
           <div class="inner">
             <h3>${(totalMoney?string(',##0.00'))!'0.00'}</h3>
             <p>累计收款</p>
           </div>
           <div class="icon">
             <i class="fa  fa-btc"></i>
           </div>
           <a href="#" class="small-box-footer">详细信息 <i class="fa fa-arrow-circle-right"></i></a>
         </div>
       </div><!-- ./col -->
       
       <div class="col-lg-3 col-xs-6">
         <!-- small box -->
         <div class="small-box bg-green">
           <div class="inner">
             <h3>${(totalOrderCount)!'0'}</h3>
             <p>累计订单数</p>
           </div>
           <div class="icon">
             <i class="fa fa-car"></i>
           </div>
           <a href="#" class="small-box-footer">详细信息 <i class="fa fa-arrow-circle-right"></i></a>
         </div>
       </div><!-- ./col -->
       <!-- fix for small devices only -->
       <div class="clearfix visible-sm-block"></div>
     </div><!-- /.row -->
     
     <div class="row">
     	<div class="col-md-12">
     		<div class="box box-info">
                <div class="box-header with-border">
                  <h3 class="box-title">最近十二个月的收益趋势图(金额（单位:万元）/月)</h3>
                  <div class="box-tools pull-right">
                    <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                    <button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                  </div>
                </div>
                <div class="box-body">
                  <div class="chart">
                    <canvas id="lineChart" style="height:400px"></canvas>
                  </div>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
     	</div>
     </div>
	
  </section><!-- /.content -->
</div><!-- /.content-wrapper -->
</@body>
<@footer>
 <script src="/static/plugins/chartjs/Chart.min.js"></script>
  <script>
 	$(function(){

        var areaChartOptions = {
          //Boolean - If we should show the scale at all
          showScale: true,
          //Boolean - Whether grid lines are shown across the chart
          scaleShowGridLines: false,
          //String - Colour of the grid lines
          scaleGridLineColor: "rgba(0,0,0,.05)",
          //Number - Width of the grid lines
          scaleGridLineWidth: 1,
          //Boolean - Whether to show horizontal lines (except X axis)
          scaleShowHorizontalLines: true,
          //Boolean - Whether to show vertical lines (except Y axis)
          scaleShowVerticalLines: true,
          //Boolean - Whether the line is curved between points
          bezierCurve: true,
          //Number - Tension of the bezier curve between points
          bezierCurveTension: 0.3,
          //Boolean - Whether to show a dot for each point
          pointDot: true,
          //Number - Radius of each point dot in pixels
          pointDotRadius: 4,
          //Number - Pixel width of point dot stroke
          pointDotStrokeWidth: 1,
          //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
          pointHitDetectionRadius: 20,
          //Boolean - Whether to show a stroke for datasets
          datasetStroke: true,
          //Number - Pixel width of dataset stroke
          datasetStrokeWidth: 2,
          //Boolean - Whether to fill the dataset with a color
          datasetFill: true,
          //String - A legend template
          legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
          //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
          maintainAspectRatio: true,
          //Boolean - whether to make the chart responsive to window resizing
          responsive: true
        };
        
        var areaChartData = {
            labels: ["一月", "二月", "三月", "四月", "五月", "六月", "七月","八月","九月","十月","十一月","十二月"],
            datasets: [
              {
                label: "Digital Goods",
                fillColor: "rgba(60,141,188,0.9)",
                strokeColor: "rgba(60,141,188,0.8)",
                pointColor: "#3b8bba",
                pointStrokeColor: "rgba(60,141,188,1)",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(60,141,188,1)",
                data: [30.05,35,36,42,50,61,72,76,78,80,82,83]
              }
            ]
          };
        
        //-------------
        //- LINE CHART -
        //--------------
        var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
        var lineChart = new Chart(lineChartCanvas);
        var lineChartOptions = areaChartOptions;
        lineChartOptions.datasetFill = false;
        lineChart.Line(areaChartData, lineChartOptions);
        
        /**
        	ajax 异步获取数据
        **/
       /*  $.post("/vns/tj/chart/12",{},function(data){
        	if(data.meta.success){
        		 var areaChartData = {
   		            labels: data.data.label,
   		            datasets: [
   		              {
   		                label: "Digital Goods",
   		                fillColor: "rgba(60,141,188,0.9)",
   		                strokeColor: "rgba(60,141,188,0.8)",
   		                pointColor: "#3b8bba",
   		                pointStrokeColor: "rgba(60,141,188,1)",
   		                pointHighlightFill: "#fff",
   		                pointHighlightStroke: "rgba(60,141,188,1)",
   		                data: data.data.list
   		              }
   		            ]
   		          };
        		 lineChart.Line(areaChartData, lineChartOptions);
        	}
        }); */
       
 	});
 </script>
</@footer>