<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Grafik_model extends CI_Model
{

    public $table = 'master_pemilih';
    public $id = 'id_pemilih';
    public $order = 'DESC';

    function __construct()
    {
        parent::__construct();
    }
    
    
    function grafik()
    {
        $data = $this->db->select('date_format(waktu,"%d-%b-%Y %h:%m") as waktu, suhu, ph, oksigen')
        //->where('waktu',date('Y-m-d h:m'))
        ->order_by('waktu','asc')
        ->get('hasil_sensor');

        $data_grafik = [];
        if ($data->num_rows() > 0) {
            foreach ($data->result() as $row) {	
                $data_grafik[] = [$row->waktu,(int)$row->suhu,'color: #039be5',(int)$row->ph,'color: #ff5722',(int)$row->oksigen,'color: #ffeb3b'];
            }
        }
        return json_encode($data_grafik);
    }

    function data_table(){
        $data = $this->db->select('date_format(waktu,"%d-%b-%Y %h:%m") as waktu, suhu, ph, oksigen')->get('hasil_sensor');
        return $data->result();
    }
}
