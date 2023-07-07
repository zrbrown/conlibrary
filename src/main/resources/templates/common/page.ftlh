<#macro headerLinks selectedNav>
    <a href="/home" <#if selectedNav == "home">class="selected" </#if>hx-boost="true" hx-target=".content">Home</a>
    <a href="/attendees" <#if selectedNav == "attendees">class="selected" </#if>hx-boost="true" hx-target=".content">Attendees</a>
    <a href="/games" <#if selectedNav == "games">class="selected" </#if>hx-boost="true" hx-target=".content">Games</a>
    <a href="/checkouts" <#if selectedNav == "checkouts">class="selected" </#if>hx-boost="true" hx-target=".content">Check In/Out</a>
    <a href="/import" <#if selectedNav == "import">class="selected" </#if>hx-boost="true" hx-target=".content">Import</a>
</#macro>
<#macro page selectedNav>
    <#if isHtmx!false>
        <div id="headerGroupLeft" class="headerGroupLeft" hx-swap-oob="true">
            <@headerLinks selectedNav/>
        </div>
        <#nested/>
    <#else>
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="utf-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Geekway Library</title>
            <link rel="shortcut icon" href="/img/favicon.png" type="image/x-icon"/>
            <link href="/css/geekway.css" rel="stylesheet" crossorigin="anonymous">
            <script src="https://unpkg.com/htmx.org@1.9.2"></script>
        </head>
        <body>
        <div class="header">
            <div id="headerGroupLeft" class="headerGroupLeft">
                <@headerLinks selectedNav/>
            </div>
            <div class="headerGroupCenter">
                <span class="headerEventTitle">Geekway to the West 2023</span>
                <button class="svgButton">
                    <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                        <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                        <g id="SVGRepo_iconCarrier">
                            <path d="M14.293 2.293a1 1 0 0 1 1.414 0l4 4a1 1 0 0 1 0 1.414l-4 4a1 1 0 0 1-1.414-1.414L16.586 8H5a1 1 0 0 1 0-2h11.586l-2.293-2.293a1 1 0 0 1 0-1.414zm-4.586 10a1 1 0 0 1 0 1.414L7.414 16H19a1 1 0 1 1 0 2H7.414l2.293 2.293a1 1 0 0 1-1.414 1.414l-4-4a1 1 0 0 1 0-1.414l4-4a1 1 0 0 1 1.414 0z"></path>
                        </g>
                    </svg>
                </button>
            </div>
            <div class="headerGroupRight">
                <span>Zack Brown</span>
            </div>
        </div>
        <div class="content">
            <#nested/>
        </div>
        <dialog id="mainDialog" hx-on="htmx:afterSettle: event.target.parentElement.showModal()">
            <form id="mainDialogContent"></form>
        </dialog>
        </body>
        </html>
    </#if>
</#macro>