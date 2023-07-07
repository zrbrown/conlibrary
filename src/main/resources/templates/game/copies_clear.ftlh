<#import "../common/checkout_status_class.ftlh" as c/>
<div id="gameTitle-${game.id()?c}" class="gameTitle<@c.checkoutStatusClass game.checkoutStatus()/>" style="grid-column: 1;" hx-swap-oob="true">
    <div class="game" hx-get="/games/${game.id()?c}/copies" hx-target="#gameCopies-${game.id()?c}" hx-swap="beforeend">${game.title()}</div>
    <button class="svgButton">
        <svg class="gameEditSvg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <g class="svgBgCarrier"></g>
            <g class="svgTracerCarrier"></g>
            <g class="svgIconCarrier"><path></path></g>
        </svg>
    </button>
    <div class="gameTitlePadding" hx-get="/games/${game.id()?c}/copies" hx-target="#gameCopies-${game.id()?c}" hx-swap="beforeend"></div>
</div>
<div id="gameCheckouts-${game.id()?c}" class="game checkoutCount<@c.checkoutStatusClass game.checkoutStatus()/>" style="grid-column: 2;" hx-get="/games/${game.id()?c}/copies" hx-target="#gameCopies-${game.id()?c}" hx-swap="beforeend" hx-swap-oob="true">${game.checkoutCount()} Checked out</div>
<div id="gameAvailability-${game.id()?c}" class="game checkoutCount<@c.checkoutStatusClass game.checkoutStatus()/>" style="grid-column: 3;" hx-get="/games/${game.id()?c}/copies" hx-target="#gameCopies-${game.id()?c}" hx-swap="beforeend" hx-swap-oob="true">${game.copies()?size - game.checkoutCount()} available</div>
<div id="gameCopy-${game.id()?c}" hx-swap-oob="delete"></div>