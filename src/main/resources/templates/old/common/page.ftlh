<#macro page>
<#import "copyNavSearch.ftlh" as cns/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="/img/favicon.png" type="image/x-icon" />
    <title>Geekway Library</title>
<#--    @Scripts.Render("~/bundles/modernizr")-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link href="/css/site.css" rel="stylesheet" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" defer integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" defer integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" defer integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js" defer crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-ajax-unobtrusive/3.2.6/jquery.unobtrusive-ajax.min.js" defer crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" defer crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validation-unobtrusive/4.0.0/jquery.validate.unobtrusive.min.js" defer crossorigin="anonymous"></script>
</head>
<body>
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="/" class="navbar-brand"><img src="/img/SmLibAppLogo.png"/></a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
    <#--                @{ var controllerName = ViewContext.RouteData.Values["Controller"].ToString();}-->
    <#--                @{ var actionName = ViewContext.RouteData.Values["Action"].ToString();}-->
    <#--                <li class="@(controllerName == "Home" ? "active" : "")">@Html.ActionLink("Home", "Index", "Home")</li>-->
    <#--                <li class="@(controllerName == "Games" || (actionName != "ListCheckedOutCopies" && controllerName == "Copies") ? "active" : "")">@Html.ActionLink("Games", "Index", "Games")</li>-->
    <#--                <li class="@(controllerName == "Attendees" ? "active" : "")">@Html.ActionLink("Attendees", "Index", "Attendees")</li>-->
    <#--                <li class="@(controllerName == "Copies" && actionName == "ListCheckedOutCopies" ? "active" : "")">@Html.ActionLink("Checked Out Copies", "ListCheckedOutCopies", "Copies")</li>-->
    <#--                <li class="@(controllerName == "Checkouts" ? "active" : "")">@Html.ActionLink("Checkout History", "Index", "Checkouts")</li>-->
                    <li class="active">
                        <a href="/home/old/index">Home</a>
                    </li>
                    <li>
                        <a href="/games/old/index">Games</a>
                    </li>
                    <li>
                        <a href="/attendees/old/index">Attendees</a>
                    </li>
                    <li>
                        <a href="/copies/old/index">Checked Out Copies</a>
                    </li>
                    <li>
                        <a href="/checkouts/old/index">Checkout History</a>
                    </li>
                </ul>
    <#--            @Html.Partial("_CopyNavSearch")-->
                <@cns.copyNavSearch/>
                <ul class="nav navbar-nav navbar-right">
    <#--                @if (User.Identity.IsAuthenticated)-->
    <#--                {-->
    <#--                <li>@Html.ActionLink("Logout", "Logout", "Account")</li>-->
    <#--                }-->
    <#--                else-->
    <#--                {-->
    <#--                <li>@Html.ActionLink("Login", "Login", "Account")</li>-->
    <#--                }-->
                    <li>
                        <a href="">Login</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="container body-content">
        <#nested/>
        <br />
        <footer>
    <#--        <p>Geekway to the West @DateTime.Now.Year</p>-->
            <p>Geekway to the West 2023</p>
        </footer>
    </div>
</body>
</html>
</#macro>