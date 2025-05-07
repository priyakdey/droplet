import Button from "@/components/button/Button.tsx";
import {
  Form,
  FormControl,
  FormField,
  FormItem, FormMessage
} from "@/components/ui/form.tsx";
import { Input } from "@/components/ui/input.tsx";
import { useAuth } from "@/hooks/useAuth.ts";
import { useProfile } from "@/hooks/useProfile.ts";
import { signup } from "@/services/auth.service.ts";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { z } from "zod";
import "./AuthForm.css";

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

function SignupForm({ handleLoginClick }: SignupFormProps) {
  const navigate = useNavigate();
  const { login } = useAuth();
  const { setProfile } = useProfile();

  const form = useForm<z.infer<typeof signupFormSchema>>({
    resolver: zodResolver(signupFormSchema),
    defaultValues: {
      name: "",
      email: "",
      password: ""
    }
  });

  function onSubmit(values: z.infer<typeof signupFormSchema>) {
    signup(values)
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
          <Button className="submit-button" type="submit" variant="default">
            Sign Up
          </Button>
        </form>
      </Form>
      <span className="form-bottom-text">Already have an account?</span>
      <Button className="submit-button" type="button" variant="default"
              onClick={handleLoginClick}>
        Login
      </Button>
    </div>
  );
}

export default SignupForm;