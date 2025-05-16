import { Button } from "@/components/ui/button.tsx";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage
} from "@/components/ui/form.tsx";
import { Input } from "@/components/ui/input.tsx";
import { emailSchema, passwordSchema } from "@/types/formSchema.ts";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import "./AuthForm.css";


const loginSchema = z.object({
  email: emailSchema,
  password: passwordSchema
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
      <form className="auth-form" onSubmit={form.handleSubmit(onSubmit)}>
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