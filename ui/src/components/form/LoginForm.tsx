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
import { useProfile } from "@/hooks/useProfile.ts";
import { authenticate } from "@/services/auth.service.ts";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { z } from "zod";
import "./AuthForm.css";

const loginFormSchema = z.object({
  email: z.string().email("Invalid email address"),
  password: z.string().min(1, "Password cannot be empty")
});

interface LoginFromProps {
  handleSignupClick: () => void;
}

export function LoginForm({ handleSignupClick }: LoginFromProps) {
  const { login } = useAuth();
  const { setProfile } = useProfile();
  const navigate = useNavigate();

  const form = useForm<z.infer<typeof loginFormSchema>>({
    resolver: zodResolver(loginFormSchema),
    defaultValues: {
      email: "",
      password: ""
    }
  });

  function onSubmit(values: z.infer<typeof loginFormSchema>) {
    authenticate(values)
      .then(data => {
        login(data.token);
        setProfile({ id: data.id, name: data.name });
        navigate("/home");
      })
      .catch(err => {
        console.error(err);
        return toast.error(err.message, { duration: 5000 });
      });
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