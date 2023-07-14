package com.geekway.conlibrary.model;

public record GameSummary(long id, String title, long copyCount, long currentCheckoutCount, long playCount,
                          CheckoutStatus checkoutStatus) {
}
