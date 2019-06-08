export interface Pager {
    currentPage: number;
    numberOfElements: number;
    totalPages: number;
    firstPage: number;
    lastPage: number;
    pages: number[];
}