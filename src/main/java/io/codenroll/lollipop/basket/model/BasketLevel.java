package io.codenroll.lollipop.basket.model;

import io.codenroll.lollipop.basket.util.Constants;

public enum BasketLevel {
    NORMAL {
        @Override
        public double calculateTotal(double total) {
            return total;
        }
    },
    SILVER {
        @Override
        public double calculateTotal(double total) {
            return total * Constants.DISCOUNT10; // Apply a 10% discount
        }
    },
    GOLD  {
        @Override
        public double calculateTotal(double total) {
            return total * Constants.DISCOUNT5; // Apply a 5% discount
        }
    };

    public abstract double calculateTotal(double total);
}
