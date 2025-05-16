import { Button } from "@/components/ui/button.tsx";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage
} from "@/components/ui/form.tsx";
import { Input } from "@/components/ui/input.tsx";
import { Label } from "@/components/ui/label.tsx";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue
} from "@/components/ui/select.tsx";
import useAuth from "@/hooks/useAuth.ts";
import useProfile from "@/hooks/useProfile.ts";
import { getAllAvailableTimezones } from "@/service/timezoneService.ts";
import { nameSchema, timezoneSchema } from "@/types/formSchema.ts";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { z } from "zod";

const editProfileSchema = z.object({
  name: nameSchema,
  timezone: timezoneSchema
});

interface EditProfileFormPropsType {
  closeSheet: () => void;
}

function EditProfileForm({ closeSheet }: EditProfileFormPropsType) {
  const [ timezones, setTimeZones ] = useState<string[]>([]);
  const { name, timezone } = useProfile();
  const { token } = useAuth();

  useEffect(() => {
    getAllAvailableTimezones(token!)
      .then(data => setTimeZones(data))
      .catch(error => {
        const description = typeof error.cause === "string"
          ? error.cause : "An unknown error occurred";
        toast.error(error.message, { description: description });
      });
  }, []);

  const form = useForm<z.infer<typeof editProfileSchema>>({
    resolver: zodResolver(editProfileSchema),
    defaultValues: {
      name: name!,
      timezone: timezone!
    }
  });

  function handleEditProfile(values: z.infer<typeof editProfileSchema>) {
    console.log("editProfile", values);
    closeSheet();
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(handleEditProfile)}>
        {/* name */}
        <FormField control={form.control} name="name" render={({ field }) => (
          <FormItem>
            <FormControl>
              <div>
                <Label htmlFor="name">Name</Label>
                <Input id="name" type="name" value={field.value}
                       onChange={field.onChange} />
              </div>

            </FormControl>
            <FormMessage />
          </FormItem>
        )}>
        </FormField>

        {/* timezone */}
        <FormField control={form.control} name="timezone"
                   render={({ field }) => (
                     <FormItem>
                       <FormControl>
                         <div>
                           <Label id="timezone-label">Select Timezone</Label>
                           <Select aria-labelledby="timezone-label"
                                   value={field.value}
                                   onValueChange={field.onChange}>
                             <SelectTrigger>
                               <SelectValue placeholder="Select Timezone" />
                             </SelectTrigger>
                             <SelectContent>
                               {
                                 timezones.map((tz) => (
                                   <SelectItem key={tz} value={tz}>
                                     {tz}
                                   </SelectItem>
                                 ))
                               }
                             </SelectContent>
                           </Select>
                         </div>
                       </FormControl>
                       <FormMessage />
                     </FormItem>
                   )}>
        </FormField>
        {/* Submit */}
        <Button type="submit" variant="default">
          Save Changes
        </Button>
      </form>
    </Form>
  );

}

export default EditProfileForm;