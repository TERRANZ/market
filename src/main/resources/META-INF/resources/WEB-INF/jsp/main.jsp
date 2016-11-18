<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="en-us">

<head>

    <!-- Basics -->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Fore ~ A Responsive Template from Blueberry</title>

    <!-- Meta -->
    <meta name="description" content="Fore is a free, responsive front end template from Blueberry, made by Jordan Bowman.">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Jordan Bowman">
    <meta name="keywords" content="awesome, hott, extremely attractive, sheer brilliance">

    <!-- CSS, Normalize First, minify for production -->
    <link href="less/fore.css" rel="stylesheet">

    <!-- Import web fonts using the link tag instead of CSS @import -->
    <link href="http://fonts.googleapis.com/css?family=Lato:300,400,900" rel="stylesheet">

    <!-- Icons from http://fontawesome.io/ -->
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

    <!-- Favicon.ico and apple-touch-icon.png (according to Apple docs). A good tool is http://iconogen.com/. Place these in the root directory. -->
    <link rel="shortcut icon" href="assets/ico/favicon.ico">
    <link rel="apple-touch-icon" href="touch-icon-iphone.png">
    <link rel="apple-touch-icon" sizes="76x76" href="touch-icon-ipad.png">
    <link rel="apple-touch-icon" sizes="120x120" href="touch-icon-iphone-retina.png">
    <link rel="apple-touch-icon" sizes="152x152" href="touch-icon-ipad-retina.png">

</head>

<body>

<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chrome/‎">install Google Chrome</a> to experience this site.</p>
<![endif]-->


<!-- Header -->
<header id="main-header">
    <div class="container">
        <h1>Fore</h1>
        <p>A free responsive site template from Blueberry</p>
    </div>
</header>


<!-- First call to action -->
<section class="call-to-action">
    <div class="cta-container cf">
        <p>Astounding call to action? Yeah, that's right.</p>
        <a class="btn" href="#">Get Started →</a>
    </div>
</section>


<!-- Macbook Section -->
<section id="macbook">
    <div class="container">
        <h2>What's this all about?</h2>
        <p>Fore was designed and built by <a href="http://www.jrdnbwmn.com" target="_blank">Jordan Bowman</a>. It is released for free on <a href="http://eatablueberry.com" target="_blank">Blueberry</a> under the <a href="http://creativecommons.org/licenses/by/3.0/" target="_blank">Creative Commons Attribution license</a>. Use it – I hope you make millions of dollars and find a hot spouse with it.</p>
        <img src="images/macbookpro.png"/>
    </div>
</section>


<!-- Summary Section -->
<section id="summary">
    <div class="container">
        <h2>What makes Fore so great?</h2>
        <p>It's responsive, beautiful, built with the latest code, includes all front end files, and (best of all) it is and always will be free. Didn't even see that comin', didya?</p>
        <a class="btn" href="#">Button</a>
    </div>
</section>


<!-- Three Images Section -->
<section id="three-images">
    <div class="container">
        <header class="body-header">
            <h2>You're probably feeling really inspired.</h2>
            <p>Pellentesque fermentum dolor. Aliquam quam lectus, facilisis auctor, ultrices ut, elementum vulputate, nunc.</p>
        </header>
        <article class="cf">
            <div class="img-circle-div">
                <img src="images/circle1.jpg"/>
                <h3>Morbi in sem quis dui</h3>
                <p>Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan porttitor, facilisis luctus, metus.</p>
            </div>
            <div class="img-circle-div">
                <img src="images/circle2.jpg"/>
                <h3>Nam nulla quam, gravida non</h3>
                <p>Pellentesque fermentum dolor. Aliquam quam lectus, facilisis auctor, ultrices tem.</p>
            </div>
            <div class="img-circle-div">
                <img src="images/circle3.jpg"/>
                <h3>Suspendisse commodo</h3>
                <p>Sed adipiscing ornare risus. Morbi est est, blandit sit amet, sagittis vel, euismod vel, velit.</p>
            </div>
        </article>
    </div>
</section>


<!-- Slider Section -->
<section class="slider-main">
    <div id="left-arrow">
        <a href="#" class="unslider-arrow prev">
            <i class="fa fa-chevron-left fa-2x"></i>
        </a>
    </div>
    <div id="right-arrow">
        <a href="#" class="unslider-arrow next">
            <i class="fa fa-chevron-right fa-2x"></i>
        </a>
    </div>
    <div class="slider">
        <ul>
            <li class="slide" id="slide1">
                <h2>This is a responsive slider.</h2>
                <p>It comes from <a href="https://github.com/idiot/unslider" target="_blank">Unslider</a>, where you'll also find issue reports (their website is out of date).</p>
                <a class="btn" href="#">Button</a>
            </li>
            <li class="slide" id="slide2">
                <h2>It also adjusts for height...</h2>
                <p>...and has keyboard support.</p>
                <a class="btn" href="#">Button</a>
            </li>
            <li class="slide" id="slide3">
                <h2>It has touch/swipe support for mobile.</h2>
                <p>That javascript comes courtesy of <a href="http://stephband.info/jquery.event.swipe/" target="_blank">@stephband</a>.</p>
                <a class="btn" href="#">Button</a>
            </li>
            <li class="slide" id="slide4">
                <h2>Vestibulum auctor dapibus.</h2>
                <p>Sed adipiscing ornare risus. Morbi est est, blandit sit amet, sagittis vel, euismod vel, velit.</p>
                <a class="btn" href="#">Button</a>
            </li>
        </ul>
    </div>
</section>


<!-- List Section -->
<section id="list">
    <div class="container">
        <header class="body-header">
            <h2>Aliquam erat volutpat.</h2>
            <p>Sed adipiscing ornare risus. Morbi est est, blandit sit amet, sagittis vel, euismod vel, velit. Pellentesque egestas sem.</p>
        </header>
        <article>
            <div class="list-row cf">
                <div class="list-item">
                    <i class="fa fa-bullhorn fa-2x"></i>
                    <h3>Aenean erat volutpat</h3>
                    <p>Pellentesque odio nisi, euismod in, pharetra a, ultricies in, diam.</p>
                </div>
                <div class="list-item">
                    <i class="fa fa-code-fork fa-2x"></i>
                    <h3>Nam nulla quam</h3>
                    <p>Donec consectetuer ligula vulputate sem tristique cursus.</p>
                </div>
            </div>
            <div class="list-row cf">
                <div class="list-item">
                    <i class="fa fa-bolt fa-2x"></i>
                    <h3>Donec quam lectus</h3>
                    <p>Donec consectetuer ligula vulputate sem tristique cursus.</p>
                </div>
                <div class="list-item">
                    <i class="fa fa-folder-open fa-2x"></i>
                    <h3>Suspendisse commodo</h3>
                    <p>Morbi est est, blandit sit amet, sagittis vel, euismod vel, velit.</p>
                </div>
            </div>
            <div class="list-row cf">
                <div class="list-item">
                    <i class="fa fa-users fa-2x"></i>
                    <h3>Quisque volutpat mattis</h3>
                    <p>Integer vitae libero ac risus egestas placerat, elementum vulputate.</p>
                </div>
                <div class="list-item" id="last-item">
                    <i class="fa fa-wrench fa-2x"></i>
                    <h3>Lorem ipsum dolor</h3>
                    <p>Pellentesque odio nisi, euismod in, pharetra a, ultricies in, diam.</p>
                </div>
            </div>
        </article>
    </div>
</section>


<!-- Second call to action -->
<section class="call-to-action cta-2">
    <div class="cta-container cf">
        <p>Astounding call to action? Yeah, that's right.</p>
        <a class="btn" href="#">Get Started →</a>
    </div>
</section>


<!-- Footer -->
<footer>
    <div class="container">
        <div class="social-boat">
            <div class="gplus" style="vertical-align: top !important;">
                <!-- Place this tag where you want the +1 button to render. -->
                <div class="g-plusone"></div>

                <!-- Place this tag after the last +1 button tag. -->
                <script type="text/javascript">
                    (function() {
                        var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
                        po.src = 'https://apis.google.com/js/plusone.js';
                        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
                    })();
                </script>
            </div>
            <div class="twitter" style="vertical-align: top !important;">
                <a href="https://twitter.com/share" class="twitter-share-button">Tweet</a>
                <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');</script>
            </div>
            <div class="facebook" style="vertical-align: top !important; margin-top: -10px;">
                <div id="fb-root"></div>
                <script>(function(d, s, id) {
                    var js, fjs = d.getElementsByTagName(s)[0];
                    if (d.getElementById(id)) return;
                    js = d.createElement(s); js.id = id;
                    js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
                    fjs.parentNode.insertBefore(js, fjs);
                }(document, 'script', 'facebook-jssdk'));</script>
                <div class="fb-like" data-layout="button_count" data-action="like" data-show-faces="false" data-share="false"></div>
            </div>
        </div>
        <div id="footer-text" class="small">
            <p>&copy; You | Photos by <a href="http://www.jrdnbwmn.com" target="_blank">Jordan Bowman</a> | Design by <a href="http://www.eatablueberry.com" target="_blank">Blueberry</a></p>
        </div>
    </div>
</footer>


<!-- Reference jQuery -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

<!-- Reference Javascript, minified for production -->
<script src="js/fore.min.js"></script>

<!-- Unslider script -->
<script>
    $(document).ready(function () {
        var unslider = $('.slider').unslider();
        $('.unslider-arrow').click(function(event) {
            event.preventDefault();
            if ($(this).hasClass('next')) {
                unslider.data('unslider')['next']();
            } else {
                unslider.data('unslider')['prev']();
            };
        });
        var unslider = $('.slider').unslider();

        unslider.on('movestart', function(e) {
            if((e.distX > e.distY && e.distX < -e.distY) || (e.distX < e.distY &&   e.distX > -e.distY)) {
                e.preventDefault();
            }
        });
    });
</script>

<!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
<script>
    (function(b,o,i,l,e,r){b.GoogleAnalyticsObject=l;b[l]||(b[l]=
            function(){(b[l].q=b[l].q||[]).push(arguments)});b[l].l=+new Date;
        e=o.createElement(i);r=o.getElementsByTagName(i)[0];
        e.src='//www.google-analytics.com/analytics.js';
        r.parentNode.insertBefore(e,r)}(window,document,'script','ga'));
    ga('create','UA-XXXXX-X');ga('send','pageview');
</script>

</body>

</html>