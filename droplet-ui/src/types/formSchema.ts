import { z } from "zod";

export const ALLOWED_SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:'\",.<>?/`~\\";

export const nameSchema = z.string()
  .min(1, "Name cannot be blank")
  .max(255, "Name can be max 255 characters");

export const emailSchema = z.string()
  .min(1, "Email is required")
  .max(254, "Email is too lengthy")
  .email("Invalid email address");

export const passwordSchema = z.string()
  .min(8, "Password must be between 8-20 characters")
  .max(20, "Password must be between 8-20 characters")
  .refine(val => /[A-Z]/.test(val), {
    message: "Password must contain an uppercase letter"
  })
  .refine(val => /[a-z]/.test(val), {
    message: "Password must contain a lowercase letter"
  })
  .refine(val => /\d/.test(val), {
    message: "Password must contain a digit"
  })
  .refine(val => new RegExp(`[${ALLOWED_SPECIAL_CHARS.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&")}]`).test(val), {
    message: `Password must contain a special character`
  });

