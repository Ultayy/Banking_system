CREATE TABLE "currencies"(
                             "id" BIGINT NOT NULL,
                             "name" CHAR(255) NOT NULL
);
ALTER TABLE
    "currencies" ADD PRIMARY KEY("id");
ALTER TABLE currencies
ALTER COLUMN id TYPE SERIAL;
CREATE TABLE "limits"(
                         "id" BIGINT NOT NULL,
                         "limit_sum" DECIMAL(8, 2) NOT NULL,
                         "set_date" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
                         "type" SMALLINT NOT NULL,
                         "currency_id" BIGINT NOT NULL
);
ALTER TABLE
    "limits" ADD PRIMARY KEY("id");
ALTER TABLE "limits" RENAME COLUMN "limit_sum" TO "current_limit";
ALTER TABLE "limits" ADD COLUMN "defaultLimit" DECIMAL(8, 2) NOT NULL DEFAULT 1000.00;
ALTER TABLE "limits" ADD COLUMN "spentAmount" DECIMAL(8, 2) NOT NULL DEFAULT 0.00;
ALTER TABLE "limits" RENAME COLUMN "defaultLimit" TO "default_limit";
ALTER TABLE "limits" RENAME COLUMN "spentAmount" TO "spent_amount";
ALTER TABLE "limits" ALTER COLUMN "set_date" TYPE timestamp;
CREATE TABLE "transactions"(
                               "id" BIGINT NOT NULL,
                               "amount" DECIMAL(8, 2) NOT NULL,
                               "status" SMALLINT NOT NULL,
                               "type" SMALLINT NOT NULL,
                               "limit_exceeded" BOOLEAN NOT NULL,
                               "transaction_date" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
                               "currency_id" BIGINT NOT NULL
);
ALTER TABLE
    "transactions" ADD PRIMARY KEY("id");
ALTER TABLE
    "limits" ADD CONSTRAINT "limits_currency_id_foreign" FOREIGN KEY("currency_id") REFERENCES "currencies"("id");
ALTER TABLE
    "transactions" ADD CONSTRAINT "transactions_currency_id_foreign" FOREIGN KEY("currency_id") REFERENCES "currencies"("id");

CREATE TABLE ExchangeRate (
                              id BIGSERIAL PRIMARY KEY,
                              currencyPair VARCHAR(255) NOT NULL,
                              rate DECIMAL(19, 4) NOT NULL,
                              date TIMESTAMP NOT NULL
);

