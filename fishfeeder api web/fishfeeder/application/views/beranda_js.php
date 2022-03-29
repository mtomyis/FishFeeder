
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">

    google.charts.load('current', {'packages':['line']});
      google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
	  var data_grafik = <?php echo $get_grafikk;?>;

      var data = new google.visualization.DataTable();

      data.addColumn('string', 'Time');
      data.addColumn('number', 'Temperature');
      data.addColumn({type: 'string', role: 'style'});
      data.addColumn('number', 'PH');
      data.addColumn({type: 'string', role: 'style'});
      data.addColumn('number', 'Oxygen Levels');
      data.addColumn({type: 'string', role: 'style'});

      data.addRows(data_grafik);

      var options = {
        chart: {
          title: 'Graphs of Temperature Sensors, PH, and Oxygen Levels',
          subtitle: 'Time Per Hour'
        },
        width: 900,
        height: 500
      };

      var chart = new google.charts.Line(document.getElementById('grafik'));

      chart.draw(data, google.charts.Line.convertOptions(options));
    }
</script>