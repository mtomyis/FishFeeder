<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Login - Data Penduduk Daftar Pemilih</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" href="apple-icon.png">
    <link rel="shortcut icon" href="favicon.ico">

    <link rel="stylesheet" href="<?php echo base_url()?>assets/css/normalize.css">
    <link rel="stylesheet" href="<?php echo base_url()?>assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="<?php echo base_url()?>assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="<?php echo base_url()?>assets/css/themify-icons.css">
    <link rel="stylesheet" href="<?php echo base_url()?>assets/css/flag-icon.min.css">
    <link rel="stylesheet" href="<?php echo base_url()?>assets/css/cs-skin-elastic.css">
    <!-- <link rel="stylesheet" href="assets/css/bootstrap-select.less"> -->
    <link rel="stylesheet" href="<?php echo base_url()?>assets/scss/style.css">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>

    <!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script> -->

</head>
<body class="my-bg">
    <div class="sufee-login d-flex align-content-center flex-wrap">
        <div class="container">
            <div style="padding: 0 35px;position: absolute;left: 105px;top: 0;width: 377px;">
                <div class="login-content" style="margin-top: 190px;">
                    <div class="login-logo">
                        <a href="index.html">
                            <img class="align-content" src="images/logo.png" alt="">
                        </a>
                    </div>
                    <div>
                        <form method="post" action="<?php echo base_url().'login'?>">
                            <div class="form-group">
                                <input style="border-radius: 50px;" type="text" name="username" class="form-control text-center" placeholder="Username">
                                <?php echo form_error('username')?>
                            </div>
                            <div class="form-group">
                                <input style="border-radius: 50px;" type="password" name="password" class="form-control text-center" placeholder="******">
                                <?php echo form_error('username')?>
                            </div>

                            <div class="form-group">
                                <!--<button type="submit" class="btn btn-success btn-flat m-b-30 m-t-30">Sign in</button>-->
                                <button style="border-radius: 50px;width: 100%;background-image: linear-gradient(to right, #c1fbaa, #007bff);" type="submit" name="login" class="btn btn-success btn-flat"><strong>Log In</strong></button>
                            </div>
                            
                            <div class="text-center">
                                <label><a style="color: #d9e2d5;" href="#">Lupa Password?</a></label>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <script src="<?php echo base_url()?>assets/js/vendor/jquery-2.1.4.min.js"></script>
    <script src="<?php echo base_url()?>assets/js/popper.min.js"></script>
    <script src="<?php echo base_url()?>assets/js/plugins.js"></script>
    <script src="<?php echo base_url()?>assets/js/main.js"></script>


</body>
</html>
