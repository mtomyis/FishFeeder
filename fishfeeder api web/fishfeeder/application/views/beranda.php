
		
        <div class="content mt-3">
            <div class="animated fadeIn">

				<div class="row">
					<div class="col-md-12 form-group  d-print-none" style="padding-top:20px">
						<a href="#" onClick="goprint()" class="btn btn-rounded btn-primary" id="btn-print"><i class="fa fa-print"></i>Print </a>
					</div>
				</div>

				<div class="row">
					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<div id="grafik" class="margin-autolr" style="width: 100%; height:550px"></div>
							</div>
						</div>
					</div><!-- /# column -->
				</div>
				
				<div class="row">
					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<table class="table">
									<thead>
										<tr>
										<th scope="col">No.</th>
										<th scope="col">Time</th>
										<th scope="col">Temperature Value</th>
										<th scope="col">Ph value</th>
										<th scope="col">Oxygen Levels</th>
										<!--<th scope="col">Keterangan</th>-->
										</tr>
									</thead>
									<tbody>
										<?php
										$inc = 0;
										 foreach($table as $row){ 
											$inc++;	 
										?>
										<tr>
											<td><?=$inc?></td>
											<td><?=$row->waktu?></td>
											<td><?=$row->suhu?></td>
											<td><?=$row->ph?></td>
											<td><?=$row->oksigen?></td>
											<!--<td><?=$row->keterangan?></td>-->
										</tr>
										<?php }?>
									</tbody>
								</table>
							</div>
						</div>
					</div><!-- /# column -->
				</div>
				
			</div>
		</div>
		

<script>
	function goprint() {
		window.print();
	}
</script>