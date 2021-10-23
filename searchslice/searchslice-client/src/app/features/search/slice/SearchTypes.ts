export interface Search {
  query: string | null;
  pageNum: number;
  pageSize: number;
  loading: boolean;
  status: number | null;
  success: boolean | null;
  message: string | null;
  data: any;
}
