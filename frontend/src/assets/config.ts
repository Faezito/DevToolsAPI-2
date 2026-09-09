export interface AppConfig {
  FRONTEND_API_KEY: string;
}

declare global {
  interface Window {
    __env: AppConfig;
  }
}