export interface ErrorStatus {
  code?: string;
  message: string;
  level: 'ERROR' | 'WARN' | 'INFO' | 'SUCCESS';
}
