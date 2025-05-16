import { Button } from "@/components/ui/button.tsx";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage
} from "@/components/ui/form.tsx";
import { Input } from "@/components/ui/input.tsx";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import "./LoginForm.css";

const ALLOWED_SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:'\",.<>?/`~\\";

const loginSchema = z.object({
  email: z.string()
    .min(1, "Email is required")
    .max(254, "Email is too lengthy")
    .email("Invalid email address"),
  password: z.string()
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
    })
});

function LoginForm() {
  const form = useForm<z.infer<typeof loginSchema>>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: "",
      password: ""
    }
  });

  function onSubmit(values: z.infer<typeof loginSchema>) {
    console.log(values);
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)}>
        {/* email */}
        <FormField control={form.control} name="email"
                   render={({ field }) => (
                     <FormItem>
                       <FormControl>
                         <Input placeholder="Email" type="email" {...field} />
                       </FormControl>
                       <FormMessage />
                     </FormItem>
                   )}>
        </FormField>
        {/* password */}
        <FormField control={form.control} name="password"
                   render={({ field }) => (
                     <FormItem>
                       <FormControl>
                         <Input placeholder="Password"
                                type="password" {...field} />
                       </FormControl>
                       <FormMessage />
                     </FormItem>
                   )}>
        </FormField>
        {/* submit */}
        <Button type="submit" variant="default">
          Login
        </Button>
      </form>
    </Form>
  );
}

export default LoginForm;