import Button from "@/components/button/Button.tsx";
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
import "./AuthForm.css";

const loginFormSchema = z.object({
  email: z.string().email("Invalid email address"),
  password: z.string().min(0, "Cannot be empty")
});

interface LoginFromProps {
  handleSignupClick: () => void;
}

export function LoginForm({ handleSignupClick }: LoginFromProps) {
  const form = useForm<z.infer<typeof loginFormSchema>>({
    resolver: zodResolver(loginFormSchema),
    defaultValues: {
      email: "",
      password: ""
    }
  });

  function onSubmit(values: z.infer<typeof loginFormSchema>) {
    console.log("Login", values);
  }

  return (
    <div className="form-container">
      <h1 className="form-title">Login</h1>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="email"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input placeholder="Email" {...field} />
                </FormControl>
              </FormItem>
            )}>
          </FormField>

          <FormField
            control={form.control}
            name="password"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input type="password"
                         placeholder="Password" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}>
          </FormField>
          <Button className="submit-button" type="submit"
                  innerHtmlText="Login" variant="default" />
        </form>
      </Form>
      <span className="form-bottom-text">Don't have an account?</span>
      <Button className="submit-button" type="button"
              innerHtmlText="Sign Up" variant="default"
              onClick={handleSignupClick} />
    </div>
  );
}