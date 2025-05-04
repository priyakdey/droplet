import Button from "@/components/button/Button.tsx";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage
} from "@/components/ui/form.tsx";
import { Input } from "@/components/ui/input.tsx";
import { useAuth } from "@/hooks/useAuth.ts";
import { signup } from "@/services/authService.ts";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import "./Form.css";

const signupFormSchema = z.object({
  name: z.string().regex(/^[a-z0-9 ]+$/i, "Name must not include special characters"),
  email: z.string().email("Invalid email address"),
  password: z.string()
    .min(8, "Password must be at least 8 characters")
    .max(20, "Password can be at max 20 characters")
    .regex(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).+$/,
      "Password must include uppercase, lowercase, number, and special character")
});

interface SignupFormProps {
  handleLoginClick: () => void;
}

export function SignupForm({ handleLoginClick }: SignupFormProps) {
  const form = useForm<z.infer<typeof signupFormSchema>>({
    resolver: zodResolver(signupFormSchema),
    defaultValues: {
      name: "",
      email: "",
      password: ""
    }
  });

  const { login } = useAuth();

  function onSubmit(values: z.infer<typeof signupFormSchema>) {
    signup(values)
      .then(data => login(data.token))
      .catch(err => console.error(err));
  }

  return (
    <div className="form-container">
      <h1 className="form-title">Sign Up</h1>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="name"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input placeholder="Name" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}>
          </FormField>

          <FormField
            control={form.control}
            name="email"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input placeholder="Email" {...field} />
                </FormControl>
                <FormMessage />
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
                  innerHtmlText="Sign Up" variant="default" />
        </form>
      </Form>
      <p className="font-light size">Already have an account?</p>
      <Button className="submit-button" type="button"
              innerHtmlText="Login" variant="default"
              onClick={handleLoginClick} />
    </div>
  );
}

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
      <p className="font-light size">Don't have an account?</p>
      <Button className="submit-button" type="button"
              innerHtmlText="Sign Up" variant="default"
              onClick={handleSignupClick} />
    </div>
  );
}

