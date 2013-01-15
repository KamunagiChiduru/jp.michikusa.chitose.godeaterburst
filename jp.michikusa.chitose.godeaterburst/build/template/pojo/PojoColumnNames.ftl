    /**
     * テーブルのカラム名を定義
     */
    public static final class Column{
<#foreach field in pojo.getAllPropertiesIterator()>
        public static final String ${field.getName()}= "${field.getName()}";
</#foreach>
        private Column(){}
    }

