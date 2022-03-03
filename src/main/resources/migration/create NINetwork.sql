CREATE TABLE "main"."network" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "name" TEXT,
  "description" TEXT,
  "table_name" TEXT,
  "input_neurons" INTEGER,
  "hidden_neurons" INTEGER,
  "output_neurons" INTEGER,
  "dest" INTEGER,
  "hidden_layer_count" INTEGER,
  "learn_rate" TEXT
);