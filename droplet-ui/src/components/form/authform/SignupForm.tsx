import { Button } from "@/components/ui/button.tsx";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage
} from "@/components/ui/form.tsx";
import { Input } from "@/components/ui/input.tsx";
import { emailSchema, nameSchema, passwordSchema } from "@/types/formSchema.ts";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import "./AuthForm.css";


const signupSchema = z.object({
  name: nameSchema,
  email: emailSchema,
  password: passwordSchema
});

function SignupForm() {
  const form = useForm<z.infer<typeof signupSchema>>({
    resolver: zodResolver(signupSchema),
    defaultValues: {
      name: "",
      email: "",
      password: ""
    }
  });

  function handleSignup(values: z.infer<typeof signupSchema>) {
    console.log(values);
  }

  return (
    <Form {...form}>
      <form className="auth-form" onSubmit={form.handleSubmit(handleSignup)}>
        {/* name */}
        <FormField control={form.control} name="name" render={({ field }) => (
          <FormItem>
            <FormControl>
              <Input placeholder="Name" type="text" {...field} />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}>
        </FormField>
        {/* email */}
        <FormField control={form.control} name="email" render={({ field }) => (
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
                         <Input placeholder="Pasword"
                                type="password" {...field} />
                       </FormControl>
                       <FormMessage />
                     </FormItem>
                   )}>
        </FormField>
        <Button type="submit" variant="default">
          Signup
        </Button>
      </form>
    </Form>
  );
}

export default SignupForm;