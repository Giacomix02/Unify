package it.univaq.disim.psvmsa.unify.storage.schema;

public enum UserSchema implements SchemaInterface{
        //TODO not sure if it's the best idea to give the row ID in the schema, maybe should be left to the
        //UserStorage class, or not be given at all.
        USER_ID(0),
        USER_NAME(1),
        USER_PASSWORD(2);

        private int row;

        UserSchema(int row) {
            this.row = row;
        }

        public int getRowIndex() {
            return row;
        }

}
