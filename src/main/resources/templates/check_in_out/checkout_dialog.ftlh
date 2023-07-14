<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Check out ${game.title()}"/>
<div class="gameScanContent">
    <div class="gameScan searchBox">
        <input type="text" id="gameScanInput" name="badgeScanInput" autofocus autocomplete="off" placeholder="${i18n("scan.badge.barcode")}" hx-post="/checkouts/barcode/attendee" hx-trigger="keyup changed delay:700ms" hx-target="#mainDialogContent"/>
        <input type="hidden" name="libraryGameCopyId" value="${game.id()}"/>
        <input type="hidden" name="gameTitle" value="${game.title()}"/>
        <button class="svgButton">
            <svg id="_x32_" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 512 512" xml:space="preserve" fill="#000000">
                    <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                <g id="SVGRepo_iconCarrier">
                    <style type="text/css">.st0{fill:#000000;}</style>
                    <g>
                        <rect y="94.103" class="st0" width="48.569" height="323.795"></rect>
                        <rect x="72.854" y="94.103" class="st0" width="24.284" height="323.795"></rect>
                        <polygon class="st0" points="121.423,94.103 121.423,417.897 145.708,417.897 169.992,417.897 169.992,94.103 145.708,94.103 "></polygon>
                        <polygon class="st0" points="194.277,94.103 194.277,417.897 218.561,417.897 242.846,417.897 242.846,94.103 218.561,94.103 "></polygon>
                        <rect x="267.13" y="94.103" class="st0" width="24.285" height="323.795"></rect>
                        <polygon class="st0" points="339.984,94.103 315.7,94.103 315.7,417.897 339.984,417.897 364.269,417.897 388.554,417.897 388.554,94.103 364.269,94.103 "></polygon>
                        <polygon class="st0" points="412.838,94.103 412.838,417.897 437.122,417.897 462.083,417.897 462.083,94.103 437.122,94.103 "></polygon>
                        <rect x="487.044" y="94.103" class="st0" width="24.956" height="323.795"></rect>
                    </g>
                </g>
                </svg>
        </button>
    </div>
</div>