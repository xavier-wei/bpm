export interface IPagination {
  currentPage: number;
  perPage: number;
  sortBy: string[];
  sortDirection: string;
}

export class Pagination implements IPagination {
  constructor(public currentPage: number, public perPage: number, public sortBy: string[], public sortDirection: string) {}
}
