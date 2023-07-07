<#import "../common/checkout_status_class.ftlh" as c/>
<#import "../common/duration_format.ftlh" as d/>
<div id="gameTitle-${game.id()?c}" class="gameTitle<@c.checkoutStatusClass game.checkoutStatus()/>" style="grid-column: 1;" hx-swap-oob="true">
    <div class="game" hx-get="/games/${game.id()?c}/copies/clear">${game.title()}</div>
    <button class="svgButton">
        <svg class="gameEditSvg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <g class="svgBgCarrier"></g>
            <g class="svgTracerCarrier"></g>
            <g class="svgIconCarrier"><path></path></g>
        </svg>
    </button>
    <div class="gameTitlePadding" hx-get="/games/${game.id()?c}/copies/clear"></div>
</div>
<div id="gameCheckouts-${game.id()?c}" class="game checkoutCount<@c.checkoutStatusClass game.checkoutStatus()/>" style="grid-column: 2;" hx-get="/games/${game.id()?c}/copies/clear" hx-swap-oob="true">${game.checkoutCount()} Checked out</div>
<div id="gameAvailability-${game.id()?c}" class="game checkoutCount<@c.checkoutStatusClass game.checkoutStatus()/>" style="grid-column: 3;" hx-get="/games/${game.id()?c}/copies/clear" hx-swap-oob="true">${game.copies()?size - game.checkoutCount()} available</div>
<#list copies as copy>
    <div id="gameCopy-${game.id()?c}" class="gameCopy<@c.checkoutStatusClass copy.checkoutStatus()/>" style="grid-row: ${copy?counter + 1};grid-column: 1;">
        <a href="/games/copies/${copy.id()?c}" hx-boost="true" hx-target="#mainDialogContent">${copy.libraryId()}</a>
    </div>
    <div id="gameCopy-${game.id()?c}" class="gameCopy<@c.checkoutStatusClass copy.checkoutStatus()/>" style="grid-row: ${copy?counter + 1};grid-column: 2;">
        ${copy.library()}
    </div>
    <#if copy.borrower()??>
        <div id="gameCopy-${game.id()?c}" class="gameCopy<@c.checkoutStatusClass copy.checkoutStatus()/>" style="grid-row: ${copy?counter + 1};grid-column: 3;">
            <a href="/attendees/${copy.borrower().id()?c}" hx-boost="true" hx-target="#mainDialogContent">${copy.borrower().name()}</a>
        </div>
    <#else>
        <div id="gameCopy-${game.id()?c}" class="gameCopy<@c.checkoutStatusClass copy.checkoutStatus()/>" style="grid-row: ${copy?counter + 1};grid-column: 3;">-</div>
    </#if>
    <div id="gameCopy-${game.id()?c}" class="gameCopy<@c.checkoutStatusClass copy.checkoutStatus()/>" style="grid-row: ${copy?counter + 1};grid-column: 4;">
        ${copy.checkoutTimeDisplay()} (<@d.format copy.checkoutDuration()/>)
    </div>
</#list>
