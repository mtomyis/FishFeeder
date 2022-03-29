<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Beranda extends CI_Controller
{
    
        
    function __construct()
    {
        parent::__construct();
        
        $this->load->helper('site_helper');
        $this->load->library('session');
        $this->load->model('grafik_model','grafik');
        
        date_default_timezone_set("ASIA/JAKARTA");


        // if(null === ($this->session->userdata('user_logedin'))) redirect('login');
    }
 
    public function index()
    {
        $data = [
            'table'    => $this->grafik->data_table(),
        ];

        $data_js = [
            'get_grafikk'  => $this->grafik->grafik(),
        ];

        $this->template->load('template','beranda',$data);
        $this->load->view('beranda_js',$data_js);
    }
}