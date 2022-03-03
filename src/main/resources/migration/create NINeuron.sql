CREATE TABLE "main"."neuron" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "layer_id" INTEGER,
  "network_name" TEXT,
  "sample_name" TEXT,
  "layer_type" INTEGER,
  "value" TEXT
);