<!DOCTYPE html>
<html>
    <head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>FISH FEEDER</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<link rel="apple-touch-icon" href="apple-icon.png">
		<link rel="shortcut icon" href="favicon.ico">

		<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/normalize.css">
		<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/font-awesome.min.css">
		<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/themify-icons.css">
		<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/flag-icon.min.css">
		<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/cs-skin-elastic.css">
        <link rel="stylesheet" href="<?php echo base_url() ?>assets/css/lib/datatable/dataTables.bootstrap.min.css">
		<!-- <link rel="stylesheet" href="assets/css/bootstrap-select.less"> -->
		<link rel="stylesheet" href="<?php echo base_url() ?>assets/scss/style.css">

		<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>
		
		<!-- Start of  Zendesk Widget script -->
		<!--<script id="ze-snippet" src="https://static.zdassets.com/ekr/snippet.js?key=3c485b08-f278-4626-82d0-e1843f18369f"> </script>-->
		<!-- End of  Zendesk Widget script -->
    </head>
    <body>
      

        <!-- Right Panel -->

        <div id="right-panel" class="right-panel">
                <?php
                echo $contents;
                ?>            
        </div><!-- /#right-panel -->
    <!-- Right Panel -->

		<!-- Javascript -->
        <script src="<?php echo base_url() ?>assets/js/vendor/jquery-2.1.4.min.js"></script>
        <script src="<?php echo base_url() ?>assets/js/popper.min.js"></script>
        <script src="<?php echo base_url() ?>assets/js/plugins.js"></script>
        <script src="<?php echo base_url() ?>assets/js/main.js"></script>

        <script src="<?php echo base_url() ?>assets/js/widgets.js"></script>
    
        <script src="<?php echo base_url() ?>assets/js/lib/data-table/datatables.min.js"></script>
        <script src="<?php echo base_url() ?>assets/js/lib/data-table/dataTables.bootstrap.min.js"></script>
        <script src="<?php echo base_url() ?>assets/js/lib/data-table/datatables-init.js"></script>
        <script type="text/javascript">
            
	        var $j = jQuery.noConflict();
            $j(document).ready(function () {
                $j("#mytable").dataTable({searching: false});
            });
        </script>

    </body>
</html>
