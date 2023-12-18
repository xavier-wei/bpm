/**
 * 單位物件
 */
export interface IDeptsModel {
    DEPT_ID?: string | null;
    DEPT_NAME?: string | null;
    SORT_ORDER?: number | null;
    DEPT_DESC?: string | null;
    IS_VALID?: string | null;
    DEPT_ID_P?: string | null;
    CREATE_USER_ID?: string | null;
    CREATE_TIMESTAMP?: Date | null;
    MODIFY_USER_ID?: string | null;
    MODIFY_TIMESTAMP?: Date | null;
    FROM_HR?: string | null;
}

export class DeptsModel implements IDeptsModel {
    constructor(
        public DEPT_ID         ?: string | null,
        public DEPT_NAME       ?: string | null,
        public SORT_ORDER      ?: number | null,
        public DEPT_DESC        ?: string | null,
        public IS_VALID        ?: string | null,
        public DEPT_ID_P        ?: string | null,
        public CREATE_USER_ID  ?: string | null,
        public CREATE_TIMESTAMP ?: Date | null,
        public MODIFY_USER_ID  ?: string | null,
        public MODIFY_TIMESTAMP ?: Date | null,
        public FROM_HR          ?: string | null,
    ) {
    }

}
