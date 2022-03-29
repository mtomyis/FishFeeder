<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Not_found extends CI_Controller
{
    
        
    function __construct()
    {
        parent::__construct();
    }

    public function index()
    {
        $this->template->load('template','not_found');
    }

}