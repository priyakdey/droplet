export class AppError extends Error {
  status: number;
  description?: string;

  constructor(status: number, message: string, description?: string) {
    super(message);
    this.status = status;
    this.description = description;
  }
}