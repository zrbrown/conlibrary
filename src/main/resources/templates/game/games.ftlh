<#import "../common/page.ftlh" as p>
<#import "../common/checkout_status_class.ftlh" as c/>
<@p.page "games">
    <div class="gameFilters">
        <div class="searchBox">
            <input type="text" placeholder="Game, Attendee, ID..."/>
            <button class="svgButton">
                <svg id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 390.704 390.704" xml:space="preserve">
                    <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                    <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                    <g id="SVGRepo_iconCarrier"><g><g><path d="M379.711,326.556L265.343,212.188c30.826-54.189,23.166-124.495-23.001-170.663c-55.367-55.366-145.453-55.366-200.818,0 c-55.365,55.366-55.366,145.452,0,200.818c46.167,46.167,116.474,53.827,170.663,23.001l114.367,114.369 c14.655,14.655,38.503,14.654,53.157,0C394.367,365.059,394.368,341.212,379.711,326.556z M214.057,214.059 c-39.77,39.771-104.479,39.771-144.25,0c-39.77-39.77-39.77-104.48,0-144.25c39.771-39.77,104.48-39.77,144.25,0 C253.828,109.579,253.827,174.29,214.057,214.059z"></path></g></g></g>
                </svg>
            </button>
        </div>
        <div>
            <label for="librarySelect">Library</label>
            <select id="librarySelect">
                <option>Common</option>
                <option>Play and Win</option>
                <option>All</option>
            </select>
        </div>
    </div>
    <div class="gameGrid">
        <#list games as game>
            <div class="gameContainer" style="grid-row: ${game?counter};">
                <div id="gameTitle-${game.id()?c}" class="gameTitle<@c.checkoutStatusClass game.checkoutStatus()/>" style="grid-column: 1;">
                    <div class="game" hx-get="/games/${game.id()?c}/copies" hx-target="#gameCopies-${game.id()?c}" hx-swap="beforeend">
                        ${game.title()}
                    </div>
                    <button class="svgButton">
                        <svg class="gameEditSvg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <g class="svgBgCarrier"></g>
                            <g class="svgTracerCarrier"></g>
                            <g class="svgIconCarrier"><path></path></g>
                        </svg>
                    </button>
                    <div class="gameTitlePadding" hx-get="/games/${game.id()?c}/copies" hx-target="#gameCopies-${game.id()?c}" hx-swap="beforeend"></div>
                </div>
                <div id="gameCheckouts-${game.id()?c}" class="game checkoutCount<@c.checkoutStatusClass game.checkoutStatus()/>" style="grid-column: 2;" hx-get="/games/${game.id()?c}/copies" hx-target="#gameCopies-${game.id()?c}" hx-swap="beforeend">
                    ${game.currentCheckoutCount()} Checked out
                </div>
                <div id="gameAvailability-${game.id()?c}" class="game checkoutCount<@c.checkoutStatusClass game.checkoutStatus()/>" style="grid-column: 3;" hx-get="/games/${game.id()?c}/copies" hx-target="#gameCopies-${game.id()?c}" hx-swap="beforeend">
                    ${game.copyCount() - game.currentCheckoutCount()} available
                </div>
                <div id="gameCopies-${game.id()?c}" class="copiesGrid">
                    <div class="gameCopyHeader" style="grid-column: 1">Library #</div>
                    <div class="gameCopyHeader" style="grid-column: 2">Library</div>
                    <div class="gameCopyHeader" style="grid-column: 3">Checked out by</div>
                    <div class="gameCopyHeader" style="grid-column: 4">Checkout time</div>
                </div>
            </div>
        </#list>
    </div>
</@p.page>